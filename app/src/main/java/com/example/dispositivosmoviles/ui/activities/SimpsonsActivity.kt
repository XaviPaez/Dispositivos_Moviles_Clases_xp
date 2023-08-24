package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.ActivitySimpsonsBinding
import com.example.dispositivosmoviles.databinding.FragmentSimpsonsBinding
import com.example.dispositivosmoviles.ui.fragments.FirstFragment
import com.example.dispositivosmoviles.ui.fragments.SimpsonsFragment
import com.example.dispositivosmoviles.ui.fragments.utilities.FragmentsManager

class SimpsonsActivity : AppCompatActivity() {

   private lateinit var  binding: ActivitySimpsonsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimpsonsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        FragmentsManager().replaceFragmet(supportFragmentManager,
            binding.frmContainer.id, SimpsonsFragment()
        )
        true
    }
}