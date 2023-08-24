package com.example.dispositivosmoviles.data.entities.simpsons

import android.annotation.SuppressLint
import com.example.dispositivosmoviles.logic.data.SimpsonsChars
import com.example.dispositivosmoviles.logic.simpsonsLogic.SimpsonsLogic

data class Doc(


    val Estado: String,
    val Genero: String,
    val Historia: String,
    val Imagen: String,
    val Nombre: String,
    val Ocupacion: String,
    val _id: String,
    val updatedAt: String
)



@SuppressLint("SuspiciousIndentation")
fun Doc.getSimpsonsChars(): SimpsonsChars{

    val a = SimpsonsChars(
        SimpsonsLogic().convertStringToInt(_id),
        Nombre,
        Estado,
        Historia,
        Imagen,
        Ocupacion
    
    )
        return a

}