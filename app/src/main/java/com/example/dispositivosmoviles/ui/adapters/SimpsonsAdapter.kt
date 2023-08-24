package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.ListadoSimpsonsBinding
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.example.dispositivosmoviles.logic.data.SimpsonsChars
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class SimpsonsAdapter(

    private var fnClick: (SimpsonsChars) -> Unit,
    private var fnSave: (SimpsonsChars) -> Boolean

) :
    RecyclerView.Adapter<SimpsonsAdapter.SimpsonsViewHolder>() {
    var items: List<SimpsonsChars> = listOf()

    class SimpsonsViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private var binding: ListadoSimpsonsBinding = ListadoSimpsonsBinding.bind(view)

        fun render(item: SimpsonsChars, fnClick: (SimpsonsChars) -> Unit,    fnSave: (SimpsonsChars) -> Boolean
        ) {

            binding.txtNombre.text = item.Nombre
            binding.txtEstado.text = item.Estado
            binding.txtOcupacion.text = item.Ocupacion

            Picasso.get().load(item.Imagen).into(binding.imgSimpsons)
            itemView.setOnClickListener {
                fnClick(item)
               Snackbar.make(
                    binding.imgSimpsons,
                   item.Nombre,
                    Snackbar.LENGTH_SHORT
               ).show()
            }
            binding.btnFav.setOnClickListener {
                var checkInsert:Boolean=false
                checkInsert=fnSave(item)
                if(checkInsert){
                    Snackbar.make(
                        binding.imgSimpsons,
                        "Se agrego a favoritos",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }else{
                    Snackbar.make(
                        binding.imgSimpsons,
                        "No se puedo agregar o Ya esta agregado",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
        }

    }

    //Los tres metodos se ejecutan cuando se ingresa un elemento de la lista

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SimpsonsAdapter.SimpsonsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SimpsonsViewHolder(
            inflater.inflate(
                R.layout.listado_simpsons,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SimpsonsAdapter.SimpsonsViewHolder, position: Int) {
        holder.render(items[position], fnClick,fnSave)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<SimpsonsChars>) {
        this.items = this.items.plus(newItems)
        notifyDataSetChanged()
    }

    fun replaceListItems(newItems: List<SimpsonsChars>) {
        this.items = newItems
        notifyDataSetChanged()
    }

}