package com.example.dispositivosmoviles.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivityDetailsMarvelBinding
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class DetailsMarvel : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsMarvelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsMarvelBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
//        var name :String? = ""
//
//        intent.extras?.let {
//
//            val name = it.getString("name")
//
//        }
//
//        if(!name.isNullOrEmpty()){
//            binding.txtName.text = name
//        }

        val item = intent.getParcelableExtra<MarvelChars>("name")

        if(item != null){
            binding.txtName.text = item.name
            Picasso.get().load(item.image).into(binding.img)
        }

    }
}