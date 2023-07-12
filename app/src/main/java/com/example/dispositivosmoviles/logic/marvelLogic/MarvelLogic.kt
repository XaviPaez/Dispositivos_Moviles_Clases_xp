package com.example.dispositivosmoviles.logic.marvelLogic

import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.data.connections.ApiConnection
import com.example.dispositivosmoviles.data.endpoints.MarvelEndpoint
import com.example.dispositivosmoviles.data.entities.marvel.Characters.database.MarvelCharsBD
import com.example.dispositivosmoviles.data.entities.marvel.Characters.getMarvelChars
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.data.entities.marvel.getMarvelCharsDB
import com.example.dispositivosmoviles.ui.fragments.utilities.DispositivosMoviles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.RuntimeException

class MarvelLogic {

    suspend fun getMarvelChars(name: String, limit: Int): ArrayList<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()


        val response =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndpoint::class.java)
                .getCharacterStartWith(name, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {

                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }

        return itemList
    }

    suspend fun getAllMarvelChars(offset: Int, limit: Int): ArrayList<MarvelChars> {

        var itemList = arrayListOf<MarvelChars>()


        val response =
            ApiConnection.getService(ApiConnection.typeApi.Marvel, MarvelEndpoint::class.java)
                .getAllMarvelChars(offset, limit)

        if (response.isSuccessful) {
            response.body()!!.data.results.forEach {
                val m = it.getMarvelChars()
                itemList.add(m)
            }
        }

        return itemList
    }

    suspend fun getAllMarvelCharDB(): List<MarvelChars> {
        var items: ArrayList<MarvelChars> = arrayListOf()
        val items_aux = DispositivosMoviles.getDbInstance().marvelDao().getAllCharacters()
        items_aux.forEach {
            items.add(
                MarvelChars(
                    it.id,
                    it.name,
                    it.comic,
                    it.image
                )
            )


        }
        return items
    }

    suspend fun insertMarvelCharsDB(items: List<MarvelChars>) {
        var itemsDB = arrayListOf<MarvelCharsBD>()
        items.forEach {
            itemsDB.add(it.getMarvelCharsDB())
        }

        DispositivosMoviles
            .getDbInstance()
            .marvelDao()
            .insertMarvelChar(itemsDB)

    }

    suspend fun getInitChars(): MutableList<MarvelChars> {

        var items = mutableListOf<MarvelChars>()
        try {

            items = MarvelLogic().getAllMarvelCharDB().toMutableList()


            if (items.isEmpty()) {

                items = (MarvelLogic().getAllMarvelChars(0, 99))
                MarvelLogic().insertMarvelCharsDB(items)
            }

        } catch (ex: Exception) {
            throw RuntimeException(ex.message)

        }
        return items


    }}