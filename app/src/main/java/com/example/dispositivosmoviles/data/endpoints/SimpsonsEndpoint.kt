package com.example.dispositivosmoviles.data.endpoints

import com.example.dispositivosmoviles.data.entities.simpsons.SimpsonsEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpsonsEndpoint {

    @GET("personajes")
    suspend fun getAllCharacters(
        @Query("limit") limit: Int


    ) : Response<SimpsonsEntity>


}