package com.project.mypokedex

import com.project.mypokedex.data.PokemonInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

    @GET("/api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") id: Int) : Call<PokemonInfo>

}