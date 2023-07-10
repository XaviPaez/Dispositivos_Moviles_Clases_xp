package com.example.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.marvel.Characters.database.MarvelCharsBD

@Dao
interface MarvelCharsDAO {

    @Query("select * from MarvelCharsBD")
    fun getAllCharacters(): List<MarvelCharsBD>

    @Query("select * from MarvelCharsBD where id= :pk")
    fun getOneCharacter(pk: Int): MarvelCharsBD

    @Insert
    fun insertMarvelChar(ch : List<MarvelCharsBD>)


}