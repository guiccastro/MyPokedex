package com.project.mypokedex.di.modules

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.project.mypokedex.database.PokemonDatabase
import com.project.mypokedex.database.dao.PokemonDao
import com.project.mypokedex.network.services.EvolutionChainService
import com.project.mypokedex.network.services.PokemonService
import com.project.mypokedex.network.services.PokemonSpeciesService
import com.project.mypokedex.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import org.json.JSONObject
import javax.inject.Singleton

private const val DATABASE_NAME = "pokemon.db"

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): PokemonDatabase {
        return Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .build()
    }

    private val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Pokemon ADD COLUMN height INTEGER NOT NULL DEFAULT -1")
        }
    }

    private val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Pokemon ADD COLUMN weight INTEGER NOT NULL DEFAULT -1")
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("CREATE TABLE pokemon_new (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `types` TEXT NOT NULL, `speciesId` INTEGER NOT NULL, `sprites` TEXT NOT NULL, `height` INTEGER NOT NULL, `weight` INTEGER NOT NULL, `evolutionChain` TEXT, `varieties` TEXT, `generation` TEXT, PRIMARY KEY(`id`))");
            val cursor = database.query("SELECT * FROM Pokemon")
            while (cursor.moveToNext()) {
                val idIndex = cursor.getColumnIndex("id")
                val nameIndex = cursor.getColumnIndex("name")
                val typesIndex = cursor.getColumnIndex("types")
                val speciesIdIndex = cursor.getColumnIndex("speciesId")
                val spritesIndex = cursor.getColumnIndex("sprites")
                val heightIndex = cursor.getColumnIndex("height")
                val weightIndex = cursor.getColumnIndex("weight")
                val speciesIndex = cursor.getColumnIndex("species")

                val id = cursor.getInt(idIndex)
                val name = cursor.getString(nameIndex)
                val types = cursor.getString(typesIndex)
                val speciesId = cursor.getInt(speciesIdIndex)
                val sprites = cursor.getString(spritesIndex)
                val height = cursor.getInt(heightIndex)
                val weight = cursor.getInt(weightIndex)
                val species = cursor.getString(speciesIndex)
                val jsonSpecies = JSONObject("""$species""")
                val evolutionChain = jsonSpecies.getString("evolutionChain")
                val varieties = jsonSpecies.getJSONArray("varieties").join("|")
                val generation = jsonSpecies.getString("generation")

                database.execSQL("INSERT INTO pokemon_new VALUES ('$id', '$name', '$types', '$speciesId', '$sprites', '$height', '$weight', '$evolutionChain', '$varieties', '$generation')")
            }

            database.execSQL("DROP TABLE Pokemon")
            database.execSQL("ALTER TABLE pokemon_new RENAME to Pokemon")
        }
    }

    private val MIGRATION_4_5 = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE Pokemon ADD COLUMN color INTEGER DEFAULT -1")
        }
    }

    @Provides
    @Singleton
    fun providePokemonDao(database: PokemonDatabase): PokemonDao {
        return database.pokemonDao()
    }

    @Provides
    @Singleton
    fun provideRepository(
        dao: PokemonDao,
        pokemonClient: PokemonService,
        evolutionChainClient: EvolutionChainService,
        pokemonSpeciesClient: PokemonSpeciesService,
        @ApplicationContext context: Context
    ): PokemonRepository {
        return PokemonRepository(
            dao,
            pokemonClient,
            evolutionChainClient,
            pokemonSpeciesClient,
            context
        )
    }
}