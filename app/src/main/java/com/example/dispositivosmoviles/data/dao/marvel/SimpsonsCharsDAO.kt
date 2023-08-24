package com.example.dispositivosmoviles.data.dao.marvel

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dispositivosmoviles.data.entities.marvel.Characters.database.MarvelCharsBD
import com.example.dispositivosmoviles.data.entities.simpsons.database.SimpsonsCharsBD
import com.example.dispositivosmoviles.logic.data.SimpsonsChars

@Dao
interface SimpsonsCharsDAO {

    @Query("select * from SimpsonsCharsBD")
    fun getAllCharacters(): List<SimpsonsChars>

    @Insert
    fun insertSimpsonsChar(ch : List<SimpsonsCharsBD>)


}