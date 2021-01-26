package com.manasomali.desafiocadastrogames.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.adapters.GamesAdapter
import com.manasomali.desafiocadastrogames.viewmodels.GamesViewModel
import kotlinx.android.synthetic.main.activity_games.*

class GamesActivity : AppCompatActivity(), GamesAdapter.OnGameClickListener  {

    private val viewModel: GamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)
        viewModel.getGames()
        viewModel.listaGames.observe(this) {
            var adapter =  GamesAdapter(it, this)
            RecyclerView_Main_Games.adapter = adapter
            RecyclerView_Main_Games.layoutManager = GridLayoutManager(this, 2)
            RecyclerView_Main_Games.setHasFixedSize(true)
        }
    }
    override fun gameClick(position: Int) {
        var gameClick = viewModel.listaGames.value?.get(position)
        Toast.makeText(this, gameClick?.nome, Toast.LENGTH_LONG).show()
    }
}