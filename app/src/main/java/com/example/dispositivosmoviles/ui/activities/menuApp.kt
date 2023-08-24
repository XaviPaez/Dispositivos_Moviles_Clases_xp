package com.example.dispositivosmoviles.ui.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivityBiometricBinding
import com.example.dispositivosmoviles.databinding.ActivityMenuAppBinding
import com.example.dispositivosmoviles.logic.data.SimpsonsChars
import java.util.Locale

class menuApp : AppCompatActivity() {

    private lateinit var binding: ActivityMenuAppBinding
    private val PERMISSION_RECORD_AUDIO = 1
    private val REQUEST_CODE_SPEECH_INPUT = 2

    override fun onStart() {
        super.onStart()
        binding.voiceCommandButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED) {
                startVoiceRecognition()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.RECORD_AUDIO
                    )
                ) {
                    // El usuario ya ha denegado el permiso en el pasado pero no ha marcado "No preguntar de nuevo"
                    showPermissionDeniedMessage()
                } else {
                    // Se solicita el permiso por primera vez o se ha marcado "No preguntar de nuevo"
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.RECORD_AUDIO),
                        PERMISSION_RECORD_AUDIO
                    )
                }
            }}}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMenuAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCamara.setOnClickListener{
            val intent= Intent(this@menuApp, CameraActivity::class.java)
            startActivity(intent)
        }

        binding.btnNotificaciones.setOnClickListener {
            val intent= Intent(this@menuApp, NotificationActivity::class.java)
            startActivity(intent)
        }

        binding.btnInfo.setOnClickListener {
            val intent= Intent(this@menuApp, PrincipalActivity::class.java)
            startActivity(intent)
        }



    }


    private fun startVoiceRecognition() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Di un comando...")
        startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (result != null && result.isNotEmpty()) {
                val spokenText = result[0]
                processVoiceCommand(spokenText)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_RECORD_AUDIO) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permiso otorgado, iniciar reconocimiento de voz
                startVoiceRecognition()
            } else {
                // Permiso denegado, mostrar mensaje
                showPermissionDeniedMessage()
            }
        }
    }

    private fun processVoiceCommand(command: String) {
        when {
            command.contains("Abrir cámara") -> {
                val cameraIntent = Intent(this, CameraActivity::class.java)
                startActivity(cameraIntent)
            }
            command.contains("abrir apis") -> {
                val galleryIntent = Intent(this, PrincipalActivity::class.java)
                startActivity(galleryIntent)
            }
            command.contains("abrir notificaciones") -> {
                val galleryIntent = Intent(this, NotificationActivity::class.java)
                startActivity(galleryIntent)
            }
            // Agregar más comandos y actividades aquí
            else -> {
                showToast("Comando de voz no reconocido")
            }
        }

    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showPermissionDeniedMessage() {
        showToast("Permiso de grabación de audio denegado. No se puede usar el reconocimiento de voz.")
    }
}


