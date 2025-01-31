package com.example.musicapp

import retrofit2.http.Query
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.Call


interface apiInterface {

    @Headers("x-rapidapi-key: d2e91e83d1msh1fdb4e8d3d8a231p115d4ajsn3b04c4679a32",
       "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(@Query("q") query: String): Call<MyData>
}