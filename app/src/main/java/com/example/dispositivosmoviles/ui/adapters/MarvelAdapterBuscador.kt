package com.example.dispositivosmoviles.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dispositivosmoviles.R
import com.example.dispositivosmoviles.data.entities.marvel.MarvelChars
import com.example.dispositivosmoviles.databinding.MarvelCharacters2Binding
import com.example.dispositivosmoviles.databinding.MarvelCharactersBinding
import com.squareup.picasso.Picasso

class MarvelAdapterBuscador(
) :
    RecyclerView.Adapter<MarvelAdapterBuscador.MarvelViewHolder>()  {
    var items: List<MarvelChars> = listOf()
    class MarvelViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private var binding: MarvelCharacters2Binding = MarvelCharacters2Binding.bind(view)

        //conectamos el objeto con el layout
        fun render(item: MarvelChars) {
            //println("Recibiendo a ${item.name}")
            binding.txtName.text = item.name
            binding.txtComic.text = item.comic
            Picasso.get().load(item.image).into(binding.imgMarvel)

        }

    }

    //Los tres metodos se ejecutan cuando se ingresa un elemento de la lista

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MarvelAdapterBuscador.MarvelViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MarvelViewHolder(
            inflater.inflate(
                R.layout.marvel_characters_2,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MarvelAdapterBuscador.MarvelViewHolder, position: Int) {
        holder.render(items[position])
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

    fun updateListItems(){
        this.items = listOf<MarvelChars>()
    }

}