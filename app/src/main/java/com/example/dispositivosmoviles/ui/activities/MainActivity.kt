package com.example.dispositivosmoviles.ui.activities


import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.location.Geocoder

import android.location.Location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts.*
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult.*
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityMainBinding
import com.example.dispositivosmoviles.logic.validator.LoginValidator
import com.example.dispositivosmoviles.utilities.MyLocationManager
import com.google.android.gms.common.api.Api.Client
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale
import java.util.UUID


//datastore de tipo preference, "name" es el nombre de la mini base de datos de clave y valor
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

//esta clase hereda de AppCompatActivity
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var  client: SettingsClient
    private lateinit var locationSettingsRequest:LocationSettingsRequest
    private  lateinit var auth : FirebaseAuth
    private val TAG = "UCE"

    private var currentLocation: Location? = null
    val speechToText =
        registerForActivityResult(StartActivityForResult()) { activityResult ->

            val sn = Snackbar.make(
                binding.titulo ,
                " ",
                Snackbar.LENGTH_LONG
            )

            var message = ""
            when (activityResult.resultCode) {

                RESULT_OK -> {


                    val msg =
                        activityResult.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                            ?.get(0).toString()


                    if (msg.isNotEmpty()) {

                        val intent = Intent(
                            Intent.ACTION_WEB_SEARCH
                        )
                        intent.setClassName(
                            "com.google.android.googlequicksearchbox",
                            "com.google.android.googlequicksearchbox.SearchActivity"
                        )
                        intent.putExtra(SearchManager.QUERY, msg)
                        startActivity(intent)

                    }
                }

                RESULT_CANCELED -> {
                    message = "Proceso Cancelado"
                    sn.setBackgroundTint(resources.getColor(R.color.red))

                }

                else -> {
                    message = "Ocurrio"

                    sn.setBackgroundTint(resources.getColor(R.color.red))

                }

            }

            sn.setText(message)
            sn.show()


        }

    @SuppressLint("MissingPermission")
    private val locationContract =
        registerForActivityResult(RequestPermission()) { isGranted ->
        when (isGranted) {
            true -> {

                client.checkLocationSettings(locationSettingsRequest).apply {

                    addOnSuccessListener {
                        val task = fusedLocationProviderClient.lastLocation
                        task.addOnSuccessListener { location ->
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.getMainLooper()
                            )
                        }
                        }
                        addOnFailureListener { ex ->
                            if (ex is ResolvableApiException) {
                                ex.startResolutionForResult(
                                    this@MainActivity,
                                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED
                                )
                            }

                        }

                    }

                    shouldShowRequestPermissionRationale(
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    )

                }

                false ->{
                    Snackbar.make(
                        binding.titulo,
                        "Denegado",
                        Snackbar.LENGTH_LONG
                    ).show()
                }

        }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, //tipo de localizacion
            2000
        )//intervalo de actualizacion
            // .setMaxUpdates(3) //cuantas veces vamos a solicitar la ubicacion
            .build()

        //clase abstracta no se puede instanciar, se esta heredando a la variable los metodos que tiene
        locationCallback = object : LocationCallback() {

            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)

                if (locationResult != null) {

                    locationResult.locations.forEach { location ->
                        currentLocation = location
                        Log.d("UCE", "Ubicacion: ${location.latitude}, ${location.longitude}")

                    }
                }
            }
        }
        client= LocationServices.getSettingsClient(this)
        locationSettingsRequest = LocationSettingsRequest.Builder().addLocationRequest(locationRequest).build()
    }

    private fun authWithFirebaseEmail(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(Constants.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication succes.",
                        Toast.LENGTH_SHORT,
                    ).show()

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(Constants.TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    private fun signInWithEmailAndPassword(email: String, password:String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    
                   // startActivity(Intent(this, menuApp::class.java))

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

    private fun recoveryPasswordWithEmail(email: String){

        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Correo de recuperacion enviado correctamente"
                    , Toast.LENGTH_SHORT).show()
                    MaterialAlertDialogBuilder(this).apply {
                        setTitle("Alerta")
                        setMessage("Correo de recuperacion enviado correctamente")
                        setCancelable(true)
                    }
                }

            }
    }

    override fun onStart() {
        super.onStart()
        initClass()


    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun initClass() {

        binding.btnIngresar.setOnClickListener {

            if(binding.textEmail.text.toString().isNotEmpty() && binding.textEmail.text.toString().isNotEmpty()){
                signInWithEmailAndPassword(
                    binding.textEmail.text.toString(),
                    binding.textPassword.text.toString()
                )
                startActivity(Intent(this,menuApp::class.java))
            }else{
                Toast.makeText(
                    baseContext,
                    "Campos vacios, ingrese su correo y contraseña",
                    Toast.LENGTH_SHORT,
                ).show()
            }



        }
        binding.registro.setOnClickListener {
            startActivity(Intent(this,RegistroActivity::class.java))

        }
        binding.btnTwitter.setOnClickListener {
            locationContract.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)



        }
        binding.btnHuella.setOnClickListener{
            val intent=Intent(this@MainActivity , BiometricActivity::class.java)
            startActivity(intent)
        }

        //como parametro necesitamos
        val appResultLocal =
            registerForActivityResult(StartActivityForResult()) { resultActivity ->

                val sn = Snackbar.make(
                    binding.titulo,
                    " ",
                    Snackbar.LENGTH_LONG
                )
                var color: Int = resources.getColor(R.color.black)
                var message =
                    when (resultActivity.resultCode) {
                        RESULT_OK -> {

                            sn.setBackgroundTint(resources.getColor(R.color.blue))
                            resultActivity.data?.getStringExtra("result").orEmpty()


                        }

                        RESULT_CANCELED -> {
                            sn.setBackgroundTint(resources.getColor(R.color.red))

                            resultActivity.data?.getStringExtra("result").orEmpty()
                        }

                        else -> {
                            "Dudoso"
                        }
                    }
                sn.setText(message)
                sn.show()


            }




        binding.btnResult.setOnClickListener {
            val intentSpeech = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intentSpeech.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intentSpeech.putExtra(

                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )


            intentSpeech.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di algo...")

            speechToText.launch(intentSpeech)

        }


    }


    private suspend fun saveDataStore(stringData: String) {
        dataStore.edit { prefs ->
            //Se puede guardar varios datos como string, boolean, integer , etc
            prefs[stringPreferencesKey("usuario")] = stringData
            prefs[stringPreferencesKey("session")] = UUID.randomUUID().toString()
            prefs[stringPreferencesKey("email")] = "dispositivosmoviles@ucce.edu.ec"


        }
    }

    private fun test(){
        var location = MyLocationManager(this)
        location.getUserLocation()
    }

}