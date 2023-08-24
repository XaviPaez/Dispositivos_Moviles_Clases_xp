package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelBinding
import com.example.dispositivosmoviles.databinding.ActivityDetailsSimpsonsBinding
import com.example.dispositivosmoviles.logic.data.SimpsonsChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.logic.simpsonsLogic.SimpsonsLogic
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsSimpsons : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsSimpsonsBinding
    private var simpsonsCharsItemsDB: MutableList<SimpsonsChars> = mutableListOf<SimpsonsChars>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsSimpsonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        val item = intent.getParcelableExtra<SimpsonsChars>("name")

        if (item != null) {
            binding.txtName.text = item.Nombre
            Picasso.get().load(item.Imagen).into(binding.imgSimpsons)
            binding.txtHistoria.text = item.Historia
            binding.btnFavoritos.setOnClickListener {
                var checkInsert: Boolean = saveSimpsonsItem(
                    SimpsonsChars(
                        item.id,
                        item.Nombre,
                        item.Ocupacion,
                        item.Historia,
                        item.Estado,
                        item.Imagen,

                    )
                )
                if (checkInsert) {
                    Snackbar.make(
                        binding.imgSimpsons,
                        "Se agrego a favoritos",
                        Snackbar.LENGTH_SHORT
                    ).show()

                } else {
                    Snackbar.make(
                        binding.imgSimpsons,
                        "No se puedo agregar o Ya esta agregado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }
    private fun saveSimpsonsItem(item: SimpsonsChars): Boolean {

        return if (item == null || simpsonsCharsItemsDB.contains(item)) {
            false
        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    SimpsonsLogic().insertSimpsonsCharstoDB(listOf(item))
                    simpsonsCharsItemsDB = SimpsonsLogic().getAllSimpsonsCharacters().toMutableList()
                }

            }
            true
        }

    }



    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                simpsonsCharsItemsDB = SimpsonsLogic().getAllSimpsonsCharacters().toMutableList()
            }
        }
    }

}