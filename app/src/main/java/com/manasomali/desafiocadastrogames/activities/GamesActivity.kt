package com.manasomali.desafiocadastrogames.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.adapters.GamesAdapter
import com.manasomali.desafiocadastrogames.classes.Jogo
import com.manasomali.desafiocadastrogames.helpers.Constants
import com.manasomali.desafiocadastrogames.viewmodels.GamesViewModel
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_games.*


@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class GamesActivity : AppCompatActivity(), GamesAdapter.OnGameClickListener  {
    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }
    private lateinit var alertDialog: AlertDialog

    private val viewModel: GamesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_games)

        viewModel.getAllGames(
            sharedPrefs.getString(Constants.KEY_EMAIL, Constants.EMPTY_STRING).toString()
        )

        viewModel.listaGames.observe(this) {
            var adapter =  GamesAdapter(it, this)
            RecyclerView_Games_Games.adapter = adapter
            RecyclerView_Games_Games.layoutManager = GridLayoutManager(this, 2)
            RecyclerView_Games_Games.setHasFixedSize(true)
            adapter.notifyDataSetChanged()
            SearchView_Games_Pesquisa.setOnQueryTextListener(object :
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        }

        Button_Games_Add.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            intent.putExtra("gameClick", Jogo("", "", "", "", ""))
            startActivity(intent)
        }
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        initViewModel()
    }
    override fun gameClick(position: Int) {
        var gameClick = viewModel.listaGames.value?.get(position) as Jogo
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("gameClick", gameClick)
        startActivity(intent)
    }
    private fun initViewModel() {
        viewModel.loading.observe(this) {
            if(it) {
                alertDialog.show()
            } else {
                alertDialog.hide()
            }
        }
    }
}