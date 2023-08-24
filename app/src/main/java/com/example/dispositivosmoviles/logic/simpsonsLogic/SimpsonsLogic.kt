package com.example.dispositivosmoviles.logic.simpsonsLogic

import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.endpoints.SimpsonsEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.Characters.database.MarvelCharsBD
import com.example.dispositivosmoviles.data.entities.marvel.Characters.getMarvelChars
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.data.entities.marvel.getMarvelCharsDB
import com.example.dispositivosmoviles.data.entities.simpsons.Doc
import com.example.dispositivosmoviles.data.entities.simpsons.database.SimpsonsCharsBD
import com.example.dispositivosmoviles.data.entities.simpsons.getSimpsonsChars
import com.example.dispositivosmoviles.logic.data.SimpsonsChars
import com.example.dispositivosmoviles.logic.data.getSimpsonsCharsDB
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.fragments.utilities.DispositivosMoviles
import java.lang.Exception
import java.lang.RuntimeException

class SimpsonsLogic {


    suspend fun getAllSimpsonsCharacters(): List<SimpsonsChars> {

        var itemList = arrayListOf<SimpsonsChars>()


        val response =
            ApiConnection.getService(ApiConnection.typeApi.Simpsons, SimpsonsEndpoint::class.java)
                .getAllCharacters(100)

        if (response.isSuccessful) {
            response.body()!!.docs.forEach {

                val s = it.getSimpsonsChars()


                itemList.add(s)

            }}

        return itemList
    }

    fun convertStringToInt(input: String): Int {

        val numericOnly = input.replace(Regex("[^0-9]"), "")


        return try {
            numericOnly.toInt()
        } catch (e: NumberFormatException) {
            println("No se puede convertir la cadena en un entero válido.")
            0
        }
    }


    suspend fun insertSimpsonsCharstoDB(items: List<SimpsonsChars>){

        var itemsDB = arrayListOf<SimpsonsCharsBD>()


        items.forEach {
            itemsDB.add(it.getSimpsonsCharsDB())
        }

        DispositivosMoviles.getDbInstance().simpsonsDao().insertSimpsonsChar(itemsDB)

    }
}