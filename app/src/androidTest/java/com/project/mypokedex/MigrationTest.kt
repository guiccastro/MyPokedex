package com.project.mypokedex

import android.util.Log
import androidx.room.migration.Migration
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.project.mypokedex.database.PokemonDatabase
import org.json.JSONObject
import org.junit.Assert.assertArrayEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        PokemonDatabase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

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

                Log.println(
                    Log.ASSERT,
                    "MIGRATION_3_4",
                    "$id - $name - $types - $speciesId - $height - $weight - $evolutionChain - $varieties - $generation"
                )

                database.execSQL("INSERT INTO pokemon_new VALUES ('$id', '$name', '$types', '$speciesId', '$sprites', '$height', '$weight', '$evolutionChain', '$varieties', '$generation')")
            }

            database.execSQL("DROP TABLE Pokemon")
            database.execSQL("ALTER TABLE pokemon_new RENAME to Pokemon")
        }
    }

    private val sprites =
        "{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/1.png\",\"other\":{\"dream_world\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/1.svg\"},\"home\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/shiny/1.png\"},\"official_artwork\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/shiny/1.png\"}},\"versions\":{\"generation_i\":{\"red_blue\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/back/1.png\",\"back_gray\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/back/gray/1.png\",\"back_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/back/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/1.png\",\"front_gray\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/gray/1.png\",\"front_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/red-blue/transparent/1.png\"},\"yellow\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/yellow/back/1.png\",\"back_gray\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/yellow/back/gray/1.png\",\"back_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/yellow/transparent/back/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/yellow/1.png\",\"front_gray\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/yellow/gray/1.png\",\"front_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-i/yellow/transparent/1.png\"}},\"generation_ii\":{\"crystal\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/back/shiny/1.png\",\"back_shiny_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/transparent/back/shiny/1.png\",\"back_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/transparent/back/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/shiny/1.png\",\"front_shiny_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/transparent/shiny/1.png\",\"front_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/crystal/transparent/1.png\"},\"gold\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/gold/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/gold/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/gold/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/gold/shiny/1.png\",\"front_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/gold/transparent/1.png\"},\"silver\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/silver/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/silver/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/silver/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/silver/shiny/1.png\",\"front_transparent\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-ii/silver/transparent/1.png\"}},\"generation_iii\":{\"emerald\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/emerald/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/emerald/shiny/1.png\"},\"firered_leafgreen\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/firered-leafgreen/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/firered-leafgreen/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/firered-leafgreen/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/firered-leafgreen/shiny/1.png\"},\"ruby_sapphire\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/ruby-sapphire/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/ruby-sapphire/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/ruby-sapphire/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iii/ruby-sapphire/shiny/1.png\"}},\"generation_iv\":{\"diamond_pearl\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/diamond-pearl/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/diamond-pearl/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/diamond-pearl/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/diamond-pearl/shiny/1.png\"},\"heartgold_soulsilver\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/heartgold-soulsilver/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/heartgold-soulsilver/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/heartgold-soulsilver/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/heartgold-soulsilver/shiny/1.png\"},\"platinum\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/platinum/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/platinum/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/platinum/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-iv/platinum/shiny/1.png\"}},\"generation_v\":{\"black_white\":{\"animated\":{\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/back/1.gif\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/back/shiny/1.gif\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/1.gif\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/animated/shiny/1.gif\"},\"back_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/back/1.png\",\"back_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/back/shiny/1.png\",\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-v/black-white/shiny/1.png\"}},\"generation_vi\":{\"omegaruby_alphasapphire\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vi/omegaruby-alphasapphire/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vi/omegaruby-alphasapphire/shiny/1.png\"},\"x_y\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vi/x-y/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vi/x-y/shiny/1.png\"}},\"generation_vii\":{\"icons\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/icons/1.png\"},\"ultra_sun_ultra_moon\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/ultra-sun-ultra-moon/1.png\",\"front_shiny\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-vii/ultra-sun-ultra-moon/shiny/1.png\"}},\"generation_viii\":{\"icons\":{\"front_default\":\"https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/versions/generation-viii/icons/1.png\"}}}}"

    private val species =
        "{\"evolutionChain\":{\"chain\":{\"evolvesTo\":[{\"evolvesTo\":[{\"evolvesTo\":[],\"pokemonId\":3}],\"pokemonId\":2}],\"pokemonId\":1}},\"generation\":\"Gen1\",\"varieties\":[1]}"

    @Test
    @Throws(IOException::class)
    fun migrate3To4() {
        var db = helper.createDatabase(TEST_DB, 3).apply {
            execSQL("INSERT INTO Pokemon VALUES (1, 'bulbasaur', '12|4', 1, '$sprites', 7, 69, '$species')")
            execSQL("INSERT INTO Pokemon VALUES (2, 'charmander', '12|4', 1, '$sprites', 7, 69, '$species')")
            close()
        }

        // Re-open the database with version 2 and provide
        // MIGRATION_1_2 as the migration process.
        db = helper.runMigrationsAndValidate(TEST_DB, 4, true, MIGRATION_3_4)

        val cursor = db.query("SELECT * FROM Pokemon")

        assertArrayEquals(
            arrayOf(
                "id",
                "name",
                "types",
                "speciesId",
                "sprites",
                "height",
                "weight",
                "evolutionChain",
                "varieties",
                "generation"
            ), cursor.columnNames
        )

        Log.println(Log.ASSERT, "TESTE", cursor.count.toString())

        // MigrationTestHelper automatically verifies the schema changes,
        // but you need to validate that the data was migrated properly.
    }
}