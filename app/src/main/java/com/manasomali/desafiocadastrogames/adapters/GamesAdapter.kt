package com.manasomali.desafiocadastrogames.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.classes.Jogo
import com.squareup.picasso.Picasso

class GamesAdapter(private val listGames: ArrayList<Jogo>, val listener: OnGameClickListener): RecyclerView.Adapter<GamesAdapter.FilmesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmesViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.game, parent, false)
        return FilmesViewHolder(itemView)
    }

    override fun getItemCount() = listGames.size

    override fun onBindViewHolder(holder: FilmesViewHolder, position: Int) {
        var game = listGames.get(position)

        holder.item_titulo_game.text = game.nome
        holder.item_ano_game.text = game.ano.toString()
        Picasso.get().load(Uri.parse(game.capa))
            .into(holder.item_image_game)
        println(Uri.parse(game.capa))
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


}