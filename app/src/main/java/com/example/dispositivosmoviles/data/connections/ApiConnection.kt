package com.example.dispositivosmoviles.data.connections

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConnection {

    fun getJikanConnecion(): Retrofit{
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create()
            )
            .build()

        return retrofit
    }


}