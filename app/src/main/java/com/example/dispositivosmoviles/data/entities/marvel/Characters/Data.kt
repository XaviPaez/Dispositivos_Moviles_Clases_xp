package com.example.dispositivosmoviles.data.entities.marvel.Characters

data class Data(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val results: List<Result>,
    val total: Int
)