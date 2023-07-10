package com.example.dispositivosmoviles.logic.List

import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars


class ListItems {

    fun returnMarvelChars(): List<MarvelChars> {
        var items = listOf<MarvelChars>(
            MarvelChars(
                1,
                "Black Cat",
                "The Amazing Spider-Man",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11144/111442876/8759849-grr.jpg"
            ),
            MarvelChars(
                2,
                "Emma Frost",
                "Gli Incredibili X-Men",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11174/111743204/8925171-emmafrost.jpg"
            ),
            MarvelChars(
                3,
                "Human Torch",
                "Fantastic Four",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8338234-b5866c56-93ff-4db0-aeca-9844ff09eac3_rw_1200.jpg"
            ),
            MarvelChars(
                4,
                "Deadpool",
                "Deadpool",
                "https://comicvine.gamespot.com/a/uploads/scale_small/12/124259/8926324-large-2680196.jpg"
            ),
            MarvelChars(
                5,
                "Daredevil",
                "Daredevil",
                "https://comicvine.gamespot.com/a/uploads/scale_small/11118/111187046/7397359-0398898002-EQH1ysWWsAA7QLf"
            )
        )

        return items
    }
}