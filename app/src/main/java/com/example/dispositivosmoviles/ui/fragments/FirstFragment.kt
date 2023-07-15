package com.example.dispositivosmoviles.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.logic.Metodos
import com.example.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic

import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvel
import com.example.dispositivosmoviles.ui.activities.dataStore
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.data.UserDataStore
import com.example.dispositivosmoviles.ui.fragments.utilities.DispositivosMoviles
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FirstFragment : Fragment() {
    private lateinit var binding: FragmentFirst1Binding

    private lateinit var lmanager: LinearLayoutManager
    private lateinit var gManager: GridLayoutManager
    private var rvAdapter: MarvelAdapter = MarvelAdapter ({ sendMarvelItem(it) },{saveMarvelItem(it)})
    private var page: Int = 1
    private var offset: Int =0
    private val limit: Int =99


    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()
    private var marvelCharsItemsDB: MutableList<MarvelChars> = mutableListOf<MarvelChars>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFirst1Binding.inflate(layoutInflater, container, false)

        // Inflate the layout for this fragment
        lmanager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.VERTICAL,
            false
        )

        gManager = GridLayoutManager(requireActivity(), 2)
        return binding.root
    }

    override fun onStart() {
        super.onStart()

        lifecycleScope.launch(Dispatchers.Main){
            getDataStore().collect{ user ->
                Log.d("UCE", user.email)
                Log.d("UCE", user.name)
                Log.d("UCE", user.session)

            }
        }


        val names = arrayListOf<String>(
            "Carlos", "Xavier", "Andres",
            "Pepe", "Mariano", "Rosa"
        )

//        val adapter = ArrayAdapter<String>(requireActivity(), R.layout.simple_layout, names)
//        binding.spinner.adapter = adapter

        chargeDataRVInit(offset,limit)
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV(offset,limit)
            binding.rvSwipe.isRefreshing = false
        }

        //Para cargar mas contenido
        binding.rvMarvelChars.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(
                    recyclerView,
                    dx,
                    dy
                ) //dy es para el scroll de abajo y dx es de izquierda a derech para buscar elementos
                if (dy > 0) {
                    val v = gManager.childCount  //cuantos elementos han pasado
                    val p = gManager.findFirstVisibleItemPosition() //posicion actual
                    val t = gManager.itemCount //cuantos tengo en total

                    //necesitamos comprobar si el total es mayor igual que los elementos que han pasado entonces ncesitamos actualizar ya que estamos al final de la lista
                    if ((v + p) >= t) {
                        Log.i("En el scrollview Offset","$offset")
                        var newItems= listOf<MarvelChars>()
                        if(offset<99){
                            updateDataRV(limit,offset)
                            lifecycleScope.launch((Dispatchers.Main)) {
                                this@FirstFragment.offset += limit
                                newItems=withContext(Dispatchers.IO) {
                                    return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))

                                }
                                rvAdapter.updateListItems(newItems)

                            }
                        }else{
                            updateDataRV(0,0)
                            lifecycleScope.launch((Dispatchers.Main)) {
                                rvAdapter.updateListItems(listOf())
                                if(offset==198){
                                    offset=0
                                }
                            }

                        }




                    }
                }
            }


        })


//        binding.txtFilter.addTextChangedListener { filteredText ->
//            val newItems = marvelCharsItems.filter { items ->
//                items.name.lowercase().contains(filteredText.toString().lowercase())
//            }
//            rvAdapter.replaceListItems(newItems)
//        }


    }


    private fun sendMarvelItem(item: MarvelChars): Unit {
        val i = Intent(requireActivity(), DetailsMarvel::class.java)
        i.putExtra("name", item)
        startActivity(i)
    }

    private fun saveMarvelItem(item: MarvelChars): Boolean {

        return if(item==null || marvelCharsItemsDB.contains(item)){
            false
        }else{

            lifecycleScope.launch(Dispatchers.Main){
                withContext(Dispatchers.IO){
                    MarvelLogic().insertMarvelCharstoDB(listOf(item))
                    marvelCharsItemsDB=MarvelLogic().getAllMarvelChardDB().toMutableList()
                }

            }
            true
        }

    }


    fun chargeDataRV(limit: Int,offset: Int) {


        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharsItems = withContext(Dispatchers.IO) {


                return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))
            }


            rvAdapter.items = marvelCharsItems

            binding.rvMarvelChars.apply {
                this.adapter = rvAdapter
                this.layoutManager = gManager
            }
            this@FirstFragment.offset += limit
        }




    }

    fun chargeDataRVInit(offset: Int,limit: Int) {

        if (Metodos().isOnline(requireActivity())) {


            lifecycleScope.launch(Dispatchers.Main) {

                marvelCharsItems = withContext(Dispatchers.IO) {
                    return@withContext (MarvelLogic().getAllMarvelChars(offset, limit))
                }

                rvAdapter.items = marvelCharsItems

                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
                this@FirstFragment.offset += limit


            }

        } else {
            Snackbar.make(
                binding.cardView2,
                "No hay conexion",
                Snackbar.LENGTH_LONG
            )
                .show()
        }
    }

    fun updateDataRV(limit: Int,offset: Int) {

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



    }


    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.Main){
            withContext(Dispatchers.IO){
                marvelCharsItemsDB=MarvelLogic().getAllMarvelChardDB().toMutableList()
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

        }



}


