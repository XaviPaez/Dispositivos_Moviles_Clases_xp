package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding
import com.example.dispositivosmoviles.logic.List.ListItems
import com.example.dispositivosmoviles.ui.activities.DetailsMarvel
import com.example.dispositivosmoviles.ui.activities.MainActivity
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirst1Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirst1Binding.inflate(
            layoutInflater,
            container,
            false
        )
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>(
            "Carlos", "Xavier", "Andres",
            "Pepe", "Mariano", "Rosa"
        )
        val adapter =
            ArrayAdapter<String>(requireActivity(),

                R.layout.spinner_item_layout , names)
        binding.spinner.adapter = adapter

        val rvAdapter = MarvelAdapter(ListItems().returnMarvelChars()){sendMarvelItem(it)}
        val rvMarvel = binding.rvMarvelChars
        rvMarvel.adapter = rvAdapter
        rvMarvel.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false)
    }

    fun sendMarvelItem(item: MarvelChars){
        val i = Intent(requireActivity(), DetailsMarvel::class.java)
        i.putExtra("name", item )

        startActivity(i)

    }

    fun chargeDataRV(){
        val rvAdapter = MarvelAdapter(ListItems().returnMarvelChars())
    {sendMarvelItem(it)}

     with(binding.rvMarvelChars){
         this.adapter = rvAdapter
         this.layoutManager = LinearLayoutManager(
             requireActivity(), LinearLayoutManager.VERTICAL, false
         )
     }

}}