package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsMarvel : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelBinding
    private var marvelCharsItemsDB: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsMarvelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
//        var name: String? = ""
//        intent.extras?.let {
//            name = it.getString("name")
//        }
//        if (!name.isNullOrEmpty()) {
//            binding.txtName.text = name
//        }

        val item = intent.getParcelableExtra<MarvelChars>("name")

        if (item != null) {
            binding.txtName.text = item.name
            Picasso.get().load(item.image).into(binding.imgMarvel)
            binding.btnFavoritos.setOnClickListener {
                var checkInsert: Boolean = saveMarvelItem(
                    MarvelChars(
                        item.id,
                        binding.txtName.text.toString(),
                        item.comic,
                        item.image
                    )
                )
                if (checkInsert) {
                    Snackbar.make(
                        binding.imgMarvel,
                        "Se agrego a favoritos",
                        Snackbar.LENGTH_SHORT
                    ).show()

                } else {
                    Snackbar.make(
                        binding.imgMarvel,
                        "No se puedo agregar o Ya esta agregado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {

        return if (item == null || marvelCharsItemsDB.contains(item)) {
            false
        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    MarvelLogic().insertMarvelCharstoDB(listOf(item))
                    marvelCharsItemsDB = MarvelLogic().getAllMarvelChardDB().toMutableList()
                }

            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                marvelCharsItemsDB = MarvelLogic().getAllMarvelChardDB().toMutableList()
            }
        }
    }
}