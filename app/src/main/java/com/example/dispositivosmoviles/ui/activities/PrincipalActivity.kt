package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding
import com.example.dispositivosmoviles.ui.fragments.ChatGptFragment
import com.example.dispositivosmoviles.ui.fragments.FavoriteFragment
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.example.dispositivosmoviles.ui.fragments.utilities.FragmentsManager
import com.google.android.material.snackbar.Snackbar

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: PrincipalActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE","Entrando a Create")
        binding = PrincipalActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    override fun onStart() {
        super.onStart()

        var name:String=""

        binding.txtName.text = "Bienvenido " + name.toString()
        FragmentsManager().replaceFragmet(
            supportFragmentManager,
            binding.frmContainer.id,
            FavoriteFragment()
        )
        initClass()

    }

    private fun initClass() {
        binding.botonRetorno.setOnClickListener {
             //el primer parametro es un filtro para que muestre solo eso en la consola al filtar por el termino y el segundo el mensaje
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            when(item.itemId) {
                R.id.Inicio -> {



                    FragmentsManager().replaceFragmet(
                        supportFragmentManager,
                        binding.frmContainer.id,
                        FirstFragment()
                    )


                    true
                }
                R.id.Favorito -> {
                    // Respond to navigation item 2 click

                }

                R.id.chatgpt -> {
              // o se crea all el fragment o nada

                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}