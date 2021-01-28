package com.manasomali.desafiocadastrogames.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.classes.Jogo
import com.squareup.picasso.Picasso

class GamesAdapter(private val listGames: ArrayList<Jogo>, val listener: OnGameClickListener): RecyclerView.Adapter<GamesAdapter.FilmesViewHolder>(),
    Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.game, parent, false)
        return FilmesViewHolder(itemView)
    }

    override fun getItemCount() = listGamesFilter.size

    override fun onBindViewHolder(holder: FilmesViewHolder, position: Int) {
        var game = listGamesFilter[position]

        holder.item_titulo_game.text = game.nome
        holder.item_ano_game.text = game.ano
        Picasso.get().load(Uri.parse(game.capa)).placeholder(R.color.black)
            .into(holder.item_image_game)
    }
    inner class FilmesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener  {
        val item_image_game: ImageView = itemView.findViewById(R.id.item_image_game)
        val item_titulo_game: TextView = itemView.findViewById(R.id.item_titulo_game)
        val item_ano_game: TextView = itemView.findViewById(R.id.item_ano_game)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            val position = adapterPosition
            if (RecyclerView.NO_POSITION != position) {
                listener.gameClick(position)
            }
        }
    }
    interface OnGameClickListener {
        fun gameClick(position: Int)
    }
    var listGamesFilter = arrayListOf<Jogo>()

    init {
        listGamesFilter = listGames
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    listGamesFilter = listGames
                } else {
                    val resultList = ArrayList<Jogo>()
                    for (row in listGames) {
                        if ((row.nome.toLowerCase().contains(charSearch.toLowerCase()))||(row.ano.toLowerCase().contains(
                                charSearch.toLowerCase()))) {
                            resultList.add(row)
                        }
                    }
                    listGamesFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = listGamesFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listGamesFilter = results?.values as ArrayList<Jogo>
                notifyDataSetChanged()
            }

        }
    }

}