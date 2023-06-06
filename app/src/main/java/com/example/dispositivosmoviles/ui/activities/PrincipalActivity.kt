package com.example.dispositivosmoviles.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding

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
        intent.extras.let {
            name = it?.getString("var1")!!
        }
        Log.d("UCE","Hola ${name}")
        binding.txtName.text = "Bienvenido " + name.toString()
        Log.d("UCE","Entrando a Start")
        initClass()

    }

    private fun initClass() {
        binding.botonRetorno.setOnClickListener {
             //el primer parametro es un filtro para que muestre solo eso en la consola al filtar por el termino y el segundo el mensaje
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}