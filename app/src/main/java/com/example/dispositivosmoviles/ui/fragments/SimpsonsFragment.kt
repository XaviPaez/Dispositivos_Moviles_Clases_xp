package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.ActivitySimpsonsBinding
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.databinding.FragmentSimpsonsBinding
import com.example.dispositivosmoviles.logic.Metodos
import com.example.dispositivosmoviles.logic.data.SimpsonsChars
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.logic.simpsonsLogic.SimpsonsLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvel
import com.example.dispositivosmoviles.ui.activities.DetailsSimpsons
import com.example.dispositivosmoviles.ui.activities.dataStore
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.adapters.SimpsonsAdapter
import com.example.dispositivosmoviles.ui.data.UserDataStore
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SimpsonsFragment : Fragment() {

    private lateinit var binding: FragmentSimpsonsBinding

    private lateinit var lmanager: LinearLayoutManager

    private var rvAdapter: SimpsonsAdapter =
        SimpsonsAdapter({ sendSimpsonslItem(it) }, { saveSimpsonsItem(it) })
    private var page: Int = 1
    private var offset: Int = 0
    private val limit: Int = 99

    private var simpsonsCharsItems: MutableList<SimpsonsChars> = mutableListOf<SimpsonsChars>()
    private var simpsonsCharsItemsDB: MutableList<SimpsonsChars> = mutableListOf<SimpsonsChars>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSimpsonsBinding.inflate(layoutInflater, container, false)

        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        chargeDataRVInit()
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV()
            binding.rvSwipe.isRefreshing = false
        }
    }

    private fun sendSimpsonslItem(item: SimpsonsChars): Unit {
        val i = Intent(requireActivity(), DetailsSimpsons::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    private fun saveSimpsonsItem(item: SimpsonsChars): Boolean {
        return if (item == null || simpsonsCharsItemsDB.contains(item)) {
            false
        } else {

            lifecycleScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    SimpsonsLogic().insertSimpsonsCharstoDB(listOf(item))
                    simpsonsCharsItemsDB = SimpsonsLogic().getAllSimpsonsCharacters().toMutableList()
                }

            }
            true
        }

        return false
    }





    fun chargeDataRV() {
        lifecycleScope.launch(Dispatchers.Main) {
            simpsonsCharsItems = withContext(Dispatchers.IO) {
                return@withContext (SimpsonsLogic().getAllSimpsonsCharacters())
            } as MutableList<SimpsonsChars>
            rvAdapter.items = simpsonsCharsItems
            binding.rvDatos.apply {
                this.adapter = rvAdapter
                this.layoutManager = lmanager
            }
        }
    }

    fun chargeDataRVInit() {

        if (Metodos().isOnline(requireActivity())) {


            lifecycleScope.launch(Dispatchers.Main) {

                simpsonsCharsItems = withContext(Dispatchers.IO) {
                    return@withContext (SimpsonsLogic().getAllSimpsonsCharacters())
                } as MutableList<SimpsonsChars>

                rvAdapter.items = simpsonsCharsItems

                binding.rvDatos.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }

            }

        } else {
            Snackbar.make(
                binding.rvDatos,
                "No hay conexion",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }

    /*fun updateDataRV(limit: Int,offset: Int) {

        var items:List<MarvelChars> = listOf()
        lifecycleScope.launch(Dispatchers.Main) {

            items = withContext(Dispatchers.IO) {


                return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))
            }


            rvAdapter.updateListItems(items)

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
        }



    }*/


    /*override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                marvelCharsItemsDB= MarvelLogic().getAllMarvelChardDB().toMutableList()
            }

        }
    }

    private fun getDataStore() =

        requireActivity().dataStore.data.map {
            // si no me devuelve nada me devuelve vacio, no  devuelve valor nos devuelve una lista de valores siempre que lo ejecutamos
                prefs ->

            UserDataStore(
                name = prefs[stringPreferencesKey("usuario")].orEmpty(),
                email = prefs[stringPreferencesKey("email")].orEmpty(),
                session = prefs[stringPreferencesKey("session")].orEmpty()


            )

        }*/



}


