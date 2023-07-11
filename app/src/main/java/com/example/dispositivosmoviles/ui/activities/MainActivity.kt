package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.example.dispositivosmoviles.ui.fragments.utilities.DispositivosMoviles
import com.google.android.material.snackbar.Snackbar

//esta clase hereda de AppCompatActivity
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //reescribir la funcion onCreate que hereda de  AppCompactActivity
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()
        initClass()

        val db = DispositivosMoviles.getDbInstance()
    }

    override fun onDestroy() {
        super.onDestroy()


    }

    @SuppressLint("ResourceAsColor")
    private fun initClass() {
        binding.btnIngresar.setOnClickListener {

            val check = LoginValidator().checkLogin(
                binding.editTextTextEmailAddress2.text.toString(),
                binding.editTextTextPassword.text.toString()
            )

            if (check) {
                var intent = Intent(
                    this,
                    PrincipalActivity::class.java
                )
                intent.putExtra(
                    "var1",
                    binding.editTextTextEmailAddress2.text.toString()
                ) //se pasa el nombre de la variable y valor
                intent.putExtra("var2", 11)
                startActivity(intent)
            } else {

                Snackbar.make(
                    binding.btnIngresar,
                    "Este es otro mensaje",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(R.color.black).show()


            }


        }
    }

}