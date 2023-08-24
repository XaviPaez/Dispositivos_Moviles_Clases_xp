package com.example.dispositivosmoviles.data.connections

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dispositivosmoviles.data.dao.marvel.MarvelCharsDAO
import com.example.dispositivosmoviles.data.dao.marvel.SimpsonsCharsDAO
import com.example.dispositivosmoviles.data.entities.marvel.Characters.database.MarvelCharsBD
import com.example.dispositivosmoviles.data.entities.simpsons.database.SimpsonsCharsBD

@Database(
    entities = [MarvelCharsBD::class, SimpsonsCharsBD::class],

    version = 1
)
abstract class MarvelConnectionDB : RoomDatabase() {

    abstract fun marvelDao(): MarvelCharsDAO

    abstract fun simpsonsDao(): SimpsonsCharsDAO
}