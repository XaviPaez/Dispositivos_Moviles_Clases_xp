package com.example.dispositivosmoviles.logic.List

import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars

class ListItems {

    fun returnMarvelChars(): List<MarvelChars> {

        val items = listOf<MarvelChars>(
            MarvelChars(
                1,
                "Spiderman",
                "Spiderman 2021",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8126579-amazing_spider-man_vol_5_54_stormbreakers_variant_textless.jpg"
            ), MarvelChars(
                2,
                "Ironman",
                "Ironman 2021",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8654427-ezgif-1-2f113089e4.jpg"
            ), MarvelChars(
                3,
                "Elektra",
                "Elektra 2021",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8324004-daredevil_woman_without_fear_vol_1_1_unknown_comic_books_exclusive_virgin_variant.jpg"
            ), MarvelChars(
                4,
                "Hulk",
                "Hulk 2021",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/7892286-immortal_hulk_vol_1_38_.jpg"
            ), MarvelChars(
                5,
                "Iceman",
                "Iceman 2021",
                "https://comicvine.gamespot.com/a/uploads/scale_small/1/14487/8582303-6562c2d0-2069-4a20-af02-4861621c0b9a.jpeg"
            ))

        return items
    }


}