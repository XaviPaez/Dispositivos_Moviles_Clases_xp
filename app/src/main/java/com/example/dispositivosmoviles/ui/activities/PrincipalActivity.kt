package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding
import com.example.dispositivosmoviles.ui.fragments.ChatGptFragment
import com.example.dispositivosmoviles.ui.fragments.FavoriteFragment
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
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

        //opcion 1
//        intent.extras!!.let {
//            var name = it.getString("var1")
//        }

        //opcion 2
        var name:String=""
       // intent.extras.let {
       //     name = it?.getString("var1")!!
       // }
        binding.txtName.text = "Bienvenido " + name.toString()

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

                    val frag = FirstFragment()
                    val transaction= supportFragmentManager.beginTransaction()
                    transaction.add(binding.frmContainer.id, frag)
                    transaction.addToBackStack(null)
                    transaction.commit() // o se crea all el fragment o nada


                    true
                }
                R.id.Favorito -> {
                    // Respond to navigation item 2 click
                    val frag = FavoriteFragment()
                    val transaction= supportFragmentManager.beginTransaction()
                    transaction.add(binding.frmContainer.id, frag)
                    transaction.addToBackStack(null)
                    transaction.commit() // o se crea all el fragment o nada


                    true
                }

                R.id.chatgpt -> {
                    val frag = ChatGptFragment()
                    val transaction= supportFragmentManager.beginTransaction()
                    transaction.add(binding.frmContainer.id, frag)
                    transaction.addToBackStack(null)
                    transaction.commit() // o se crea all el fragment o nada

                    true
                }
                else -> false
            }
        }
    }
}