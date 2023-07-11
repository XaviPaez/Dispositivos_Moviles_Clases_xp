package com.example.dispositivosmoviles.data.entities.marvel

import android.os.Parcelable
import com.example.dispositivosmoviles.data.entities.marvel.Characters.database.MarvelCharsBD
import kotlinx.parcelize.Parcelize

@Parcelize
data class MarvelChars(
    val id: Int,
    val name: String,
    val comic: String,
    val image: String
): Parcelable

fun MarvelChars.getMarvelCharsDB(): MarvelCharsBD{
     return MarvelCharsBD(id,name,comic,image)


}