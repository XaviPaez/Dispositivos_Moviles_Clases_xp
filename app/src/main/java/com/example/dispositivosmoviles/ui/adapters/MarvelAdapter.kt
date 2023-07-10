package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

//Unit indica que la funcion no retorna nada
class MarvelAdapter(
    private var fnClick: (MarvelChars) -> Unit
) :
    RecyclerView.Adapter<MarvelAdapter.MarvelViewHolder>() {
    var items: List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private var binding: MarvelCharactersBinding = MarvelCharactersBinding.bind(view)

        //conectamos el objeto con el layout
        fun render(item: MarvelChars, fnClick: (MarvelChars) -> Unit) {
            //println("Recibiendo a ${item.name}")
            binding.textName.text = item.name
            binding.textComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)
            itemView.setOnClickListener {
                fnClick(item)
//                Snackbar.make(
//                    binding.imgMarvel,
//                    item.name,
//                    Snackbar.LENGTH_SHORT
//                ).show()
            }
        }

    }

    //Los tres metodos se ejecutan cuando se ingresa un elemento de la lista

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapter.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapter.MarvelViewHolder, position: Int) {
        holder.render(items[position], fnClick)
    }

    override fun getItemCount(): Int = items.size

    fun updateListItems(newItems: List<MarvelChars>){
        this.items=this.items.plus(newItems)
        notifyDataSetChanged()
    }
    fun replaceListItems(newItems: List<MarvelChars>){
        this.items=newItems
        notifyDataSetChanged()
    }

}