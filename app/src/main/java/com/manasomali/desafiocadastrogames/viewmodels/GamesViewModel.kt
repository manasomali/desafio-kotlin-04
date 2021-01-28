package com.manasomali.desafiocadastrogames.viewmodels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.manasomali.desafiocadastrogames.classes.Jogo
import java.time.Instant
import java.time.format.DateTimeFormatter

class GamesViewModel(): ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var listaGames = MutableLiveData<ArrayList<Jogo>>()
    val db = FirebaseFirestore.getInstance()
    var stateAddGame: MutableLiveData<Boolean> = MutableLiveData()
    var stateGetGame: MutableLiveData<Boolean> = MutableLiveData()
    var stateDelGame: MutableLiveData<Boolean> = MutableLiveData()

    fun updateListaGames(lista: ArrayList<Jogo>) {
        listaGames.value = lista
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun addGame(nome: String, ano: String, descricao: String, capa: String, user: String) {
        loading.value = true
        var timestamp = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
        var docRef = db.collection("users").document(user).collection("games").document(timestamp)
        Log.d("TAGadd", timestamp)
        val jogo = hashMapOf(
            "nome" to nome,
            "ano" to ano,
            "descricao" to descricao,
            "capa" to capa,
            "id" to timestamp
        )
        docRef.set(jogo)
            .addOnSuccessListener {
                stateAddGame.value = true
                Log.d("TAG", "DocumentSnapshot successfully written!")
            }
            .addOnFailureListener { e ->
                stateAddGame.value = false
                Log.w("TAG", "Error writing document", e)
            }
            .addOnCompleteListener {
                loading.value = false
            }
    }
    fun editGame(id:String, nome: String, ano: String, descricao: String, capa: String, user: String) {
        loading.value = true
        var docRef = db.collection("users").document(user).collection("games").document(id)
        Log.d("TAGedit", id)
        val jogo = hashMapOf(
            "nome" to nome,
            "ano" to ano,
            "descricao" to descricao,
            "capa" to capa,
            "id" to id
        )
        docRef.set(jogo)
            .addOnSuccessListener {
                stateAddGame.value = true
                Log.d("TAG", "DocumentSnapshot successfully edited!")
            }
            .addOnFailureListener { e ->
                stateAddGame.value = false
                Log.w("TAG", "Error writing document", e)
            }
            .addOnCompleteListener {
                loading.value = false
            }
    }
    fun getAllGames(user: String) {
        loading.value = true
        var lista = ArrayList<Jogo>()
        db.collection("users").document(user).collection("games").orderBy("id")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    var jogo = Jogo(
                        document.get("id").toString(),
                        document.get("nome").toString(),
                        document.get("ano").toString(),
                        document.get("descricao").toString(),
                        document.get("capa").toString()
                    )
                    lista.add(jogo)
                }
                stateGetGame.value = true
            }
            .addOnFailureListener { exception ->
                stateGetGame.value = false
                Log.d("TAG", "Error getting documents: ", exception)
            }
            .addOnCompleteListener {
                loading.value = false
                updateListaGames(lista)
            }
    }
    fun delGame(user: String, id: String) {
        loading.value = true
        db.collection("users").document(user).collection("games").document(id)
            .delete()
            .addOnSuccessListener {
                Log.d("TAG", "Successfully deleting document")
                stateDelGame.value = true
            }
            .addOnFailureListener { exception ->
                stateDelGame.value = false
                Log.d("TAG", "Error deleting document", exception)
            }
            .addOnCompleteListener {
                loading.value = false
            }
    }
}