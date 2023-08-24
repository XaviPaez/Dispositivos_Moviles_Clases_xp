package com.example.dispositivosmoviles.logic.data

import android.os.Parcelable
import com.example.dispositivosmoviles.data.entities.simpsons.database.SimpsonsCharsBD
import kotlinx.parcelize.Parcelize

@Parcelize
data class SimpsonsChars (
    val id: Int,
    val Nombre: String,
    val Estado: String,
    val Historia: String,
    val Imagen: String,
    val Ocupacion: String,

) : Parcelable

fun SimpsonsChars.getSimpsonsCharsDB(): SimpsonsCharsBD{
    return SimpsonsCharsBD(
        id,
        Nombre,
        Estado,
        Historia,
        Imagen,
        Ocupacion


    )
}
