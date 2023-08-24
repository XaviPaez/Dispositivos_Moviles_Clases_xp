package com.example.dispositivosmoviles.data.entities.simpsons.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class SimpsonsCharsBD(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val Estado: String,
    val Historia: String,
    val Imagen: String,
    val Nombre: String,
    val Ocupacion: String
) : Parcelable
