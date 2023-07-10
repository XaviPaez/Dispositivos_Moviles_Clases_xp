package com.example.dispositivosmoviles.data.entities.marvel.Characters

import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars


data class Result(
    val comics: Comics,
    val description: String,
    val events: Events,
    val id: Int,
    val modified: String,
    val name: String,
    val resourceURI: String,
    val series: Series,
    val stories: Stories,
    val thumbnail: Thumbnail,
    val urls: List<Url>
)

fun Result.getMarvelChars(): MarvelChars {
    var comic: String = "No disponible"
    if (comics.items.isNotEmpty()) {
        comic = comics.items[0].name
    }

    val a= MarvelChars(
        id,
        name,
        comic,
        thumbnail.path + "." + thumbnail.extension,
    )
    return a
}