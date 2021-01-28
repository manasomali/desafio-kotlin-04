package com.manasomali.desafiocadastrogames.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel(): ViewModel() {

    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var stateRegister: MutableLiveData<Boolean> = MutableLiveData()
    var stateLogin: MutableLiveData<Boolean> = MutableLiveData()


    fun cadastroUsuarioFirebase(email: String, senha: String) {
        loading.value = true
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                loading.value = false
                when {
                    task.isSuccessful -> {
                        stateRegister.value = true
                    }
                    else -> {
                        println("Authentication Failed!")
                        stateRegister.value = false
                        loading.value = false
                    }
                }
            }
    }
    fun loginUsuarioFirebase(email: String, senha: String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task: Task<AuthResult?> ->

                when {
                    task.isSuccessful -> {
                        stateLogin.value = true
                    }
                    else -> {
                        stateLogin.value = false
                        loading.value = false
                    }
                }
            }
    }
}