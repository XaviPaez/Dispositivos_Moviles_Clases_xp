package com.example.dispositivosmoviles.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding

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

        val names = arrayListOf<String>("Carlos", "Xavier", "Andres", "Mike", "Pablo", "Mika")

    val adapter = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item, names)
        binding.spinner.adapter = adapter
        binding.listView.adapter = adapter


}}