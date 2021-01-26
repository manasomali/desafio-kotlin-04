package com.manasomali.desafiocadastrogames.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manasomali.desafiocadastrogames.classes.Jogo
import kotlinx.coroutines.launch

class GamesViewModel(): ViewModel() {

    var listaGames = MutableLiveData<ArrayList<Jogo>>()

    fun getGames() {
        viewModelScope.launch {
            listaGames.value = arrayListOf(Jogo(1,"jogo1",1996,"descricao1", "https://images-na.ssl-images-amazon.com/images/I/812CrerASqL._AC_SX466_.jpg"),
                                            Jogo(2,"jogo2",1997,"descricao2", "https://images-na.ssl-images-amazon.com/images/I/812CrerASqL._AC_SX466_.jpg"),
                                            Jogo(3,"jogo3",1998,"descricao3", "https://images-na.ssl-images-amazon.com/images/I/812CrerASqL._AC_SX466_.jpg"))
        }
    }

}