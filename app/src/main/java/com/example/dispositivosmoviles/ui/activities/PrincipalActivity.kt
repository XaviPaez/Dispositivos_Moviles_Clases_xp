package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.example.dispositivosmoviles.ui.fragments.ChatGptFragment
import com.example.dispositivosmoviles.ui.fragments.FavoriteFragment
import com.example.dispositivosmoviles.ui.fragments.utilities.FragmentsManager

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: PrincipalActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("UCE", "Entrando a Create")
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
        var name: String = ""
        /*intent.extras.let {
            name = it?.getString("var1")!!
        }*/
        Log.d("UCE", "Hola ${name}")

        Log.d("UCE", "Entrando a Start")
        initClass()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Inicio -> {

//                    val frag = FirstFragment() //crea una instancia del fragment que se quiere agregar
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.replace(binding.frmContainer.id, frag) //se coloca el id del contenedor(framelayout) y se pasa la instancia del fragment que se quiere agregar
//                    //con .add se inserta otro fragment(mas contenido encima del anterior) encima
//                    transaction.addToBackStack(null)
//                    transaction.commit() //guarda los cambios

                    FragmentsManager().replaceFragmet(supportFragmentManager,
                        binding.frmContainer.id, FirstFragment())
                    true
                }

                R.id.Favorito -> {
//                    val frag = FavoritesFragment() //crea una instancia del fragment que se quiere agregar
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.replace(binding.frmContainer.id, frag) //se coloca el id del contenedor(framelayout) y se pasa la instancia del fragment que se quiere agregar
//                    //con .add se inserta otro fragment(mas contenido encima del anterior) encima
//                    transaction.addToBackStack(null)
//                    transaction.commit() //guarda los cambios

                    FragmentsManager().replaceFragmet(supportFragmentManager,
                        binding.frmContainer.id, FavoriteFragment()
                    )
                    true
                }

                R.id.chatgpt -> {
//                    val frag = ChatGptFragment() //crea una instancia del fragment que se quiere agregar
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.replace(binding.frmContainer.id, frag) //se coloca el id del contenedor(framelayout) y se pasa la instancia del fragment que se quiere agregar
//                    //con .add se inserta otro fragment(mas contenido encima del anterior) encima
//                    transaction.addToBackStack(null)
//                    transaction.commit() //guarda los cambios

                    FragmentsManager().replaceFragmet(supportFragmentManager,
                        binding.frmContainer.id, ChatGptFragment())
                    true
                }

                else -> false
            }
        }

    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    private fun initClass() {

    }
}