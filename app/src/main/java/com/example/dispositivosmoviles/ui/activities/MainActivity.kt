package com.example.dispositivosmoviles.ui.activities

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.example.dispositivosmoviles.ui.fragments.utilities.DispositivosMoviles
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

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

                lifecycleScope.launch(Dispatchers.IO){
                    saveDataStore(binding.editTextTextEmailAddress2.text.toString())

                }



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
                ).setBackgroundTint(R.color.black)
                    .show()


            }




        }
        binding.btnTwitter.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com.ec"))
//            startActivity(intent)
            val intent = Intent(Intent.ACTION_WEB_SEARCH
            )
            intent.setClassName("com.google.android.googlequicksearchbox",
                "com.google.android.googlequicksearchbox.SearchActivity")
            intent.putExtra(SearchManager.QUERY, "Liga de quito")
            startActivity(intent)
        }

        val appResultLocal = registerForActivityResult(StartActivityForResult()){



            resultActivity ->

            when(resultActivity.resultCode){
                RESULT_OK -> {
                    Snackbar.make(binding.textView, "Resultado Exitoso", Snackbar.LENGTH_LONG).show()}
                RESULT_CANCELED -> {
                    Snackbar.make(binding.textView, "Resultado Fallido", Snackbar.LENGTH_LONG).show()
                }
                else -> {
                    Snackbar.make(binding.textView, "No tengo idea", Snackbar.LENGTH_LONG).show()}
            }
            }

        binding.btnFacebook.setOnClickListener {

            val resIntent = Intent(this, ResultActivity::class.java)
            appResultLocal.launch(resIntent)
        }

    }

    private suspend fun saveDataStore(stringData: String){
        dataStore.edit {prefs->
            prefs[stringPreferencesKey("usuario")]= stringData
            prefs[stringPreferencesKey("session")]= UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")]= "dispositivosmoviles@uce.edu.ec"

        }
    }

}