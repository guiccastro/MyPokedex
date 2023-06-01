package com.project.mypokedex.client

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {

    @GET("/api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<String>

    @GET("/api/v2/pokemon/{id}")
    fun getPokemon(@Path("id") name: String): Call<String>

    @GET("/api/v2/pokemon?limit=100000&offset=0")
    fun getBasicKeys(): Call<String>

}