package com.project.mypokedex.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.project.mypokedex.model.Pokemon

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(pokemon: Pokemon)

    @Delete
    fun delete(pokemon: Pokemon)

    @Query("DELETE FROM Pokemon WHERE id = :id")
    fun delete(id: Int)

    @Query("SELECT * FROM Pokemon ORDER BY id")
    fun getAll(): List<Pokemon>

    @Query("SELECT * FROM Pokemon WHERE id = :id")
    fun getById(id: Int): Pokemon?

}