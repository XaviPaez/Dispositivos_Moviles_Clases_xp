package com.example.dispositivosmoviles.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFavoriteBinding
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapterBuscador
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var gManager: GridLayoutManager
    private lateinit var lmanager: LinearLayoutManager

    private var rvAdapter: MarvelAdapterBuscador = MarvelAdapterBuscador()

    private var marvelCharsItems: MutableList<MarvelChars> = mutableListOf<MarvelChars>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)

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
        chargeDataRV()
//        binding.rvSwipe.setOnRefreshListener {
//            chargeDataRV()
//            binding.rvSwipe.isRefreshing = false
//        }
//        //Para cargar mas contenido
//        binding.rvMarvelChars.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(
//                    recyclerView,
//                    dx,
//                    dy
//                ) //dy es para el scroll de abajo y dx es de izquierda a derech para buscar elementos
//
//                if (dy > 0) {
//                    val v = lmanager.childCount  //cuantos elementos han pasado
//                    val p = lmanager.findFirstVisibleItemPosition() //posicion actual
//                    val t = lmanager.itemCount //cuantos tengo en total
//
//                    //necesitamos comprobar si el total es mayor igual que los elementos que han pasado entonces ncesitamos actualizar ya que estamos al final de la lista
//                    if ((v + p) >= t) {
//                        chargeDataRV()
//                        lifecycleScope.launch((Dispatchers.IO)) {
//                            val newItems = MarvelLogic().getAllMarvelChars(0, 99)
//                            withContext(Dispatchers.Main) {
//                                rvAdapter.updateListItems(newItems)
//                            }
//                        }
//                    }
//                }
//            }
//
//
//        })

        binding.txtFilter.addTextChangedListener { filteredText ->
            if (filteredText.toString().isNullOrEmpty()) {
                chargeDataRVVacios()
            } else {

                val newItems = marvelCharsItems.filter { items ->
                    items.name.lowercase().contains(filteredText.toString().lowercase())
                }
                rvAdapter.replaceListItems(newItems)
                binding.rvMarvelChars.apply {
                    this.adapter = rvAdapter
                    this.layoutManager = gManager
                }
            }
        }

    }


    fun chargeDataRV() {


        lifecycleScope.launch(Dispatchers.Main) {

            marvelCharsItems = withContext(Dispatchers.IO) {


                return@withContext (MarvelLogic().getAllMarvelChars(0, 99))
            }


        }


    }


    fun chargeDataRVVacios() {
        rvAdapter.items = listOf<MarvelChars>()

        binding.rvMarvelChars.apply {
            this.adapter = rvAdapter
        }
    }


}