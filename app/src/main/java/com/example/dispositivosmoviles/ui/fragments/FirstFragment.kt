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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.FragmentFirst1Binding
import com.example.dispositivosmoviles.databinding.PrincipalActivityBinding
import com.example.dispositivosmoviles.logic.List.ListItems
import com.example.dispositivosmoviles.logic.jikanLogic.JikanAnimeLogic
import com.example.dispositivosmoviles.logic.marvelLogic.MarvelLogic
import com.example.dispositivosmoviles.ui.activities.DetailsMarvel
import com.example.dispositivosmoviles.ui.activities.MainActivity
import com.example.dispositivosmoviles.ui.adapters.MarvelAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment() {

    private lateinit var binding: FragmentFirst1Binding
    private lateinit var lmanager: LinearLayoutManager
    private var rvAdapter: MarvelAdapter = MarvelAdapter { sendMarvelItem(it) }

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
        lmanager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        return binding.root
    }


    override fun onStart() {
        super.onStart()
        val names = arrayListOf<String>(
            "Carlos", "Xavier", "Andres",
            "Pepe", "Mariano", "Rosa"
        )
        val adapter =
            ArrayAdapter<String>(
                requireActivity(),

                R.layout.spinner_item_layout, names
            )

        binding.spinner.adapter = adapter
        chargeDataRV("Spider")
        binding.rvSwipe.setOnRefreshListener {
            chargeDataRV("Spider")
            binding.rvSwipe.isRefreshing = false
        }

        //Para  cargar mas contenido
        binding.rvMarvelChars.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val elementos = lmanager.childCount
                val posicion = lmanager.findFirstVisibleItemPosition()
                val tamanio = lmanager.itemCount
                if (dy > 0) {
                    if ((elementos + posicion) >= tamanio) {
                        chargeDataRV("Spider")
                        lifecycleScope.launch((Dispatchers.IO)) {
                            val items = JikanAnimeLogic().getAllAnimes()
                            withContext(Dispatchers.Main) {
                                rvAdapter.updateListItems(items)
                            }
                        }
                    }

                }
            }
        })

    }

    fun corrutine() {
        lifecycleScope.launch(Dispatchers.Main) {
            lifecycleScope.launch(Dispatchers.IO) {
                var name = "Xavier"
                name = withContext(Dispatchers.IO) {
                    name = "Sebastian"
                    return@withContext name
                }
                binding.cardView2.radius
            }
        }
    }

    fun sendMarvelItem(item: MarvelChars) {
        val i = Intent(requireActivity(), DetailsMarvel::class.java)
        i.putExtra("name", item)

        startActivity(i)

    }

    fun chargeDataRV(search: String) {

        lifecycleScope.launch(Dispatchers.IO) {

            rvAdapter.items = JikanAnimeLogic().getAllAnimes()

            withContext(Dispatchers.Main) {
                with(binding.rvMarvelChars) {
                    this.adapter = rvAdapter
                    this.layoutManager = lmanager
                }


            }
        }
    }
}