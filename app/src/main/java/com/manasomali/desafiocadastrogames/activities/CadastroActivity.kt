package com.manasomali.desafiocadastrogames.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.manasomali.desafiocadastrogames.helpers.Constants
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.viewmodels.AuthViewModel
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }
    private lateinit var alertDialog: AlertDialog
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        firebaseAuth = FirebaseAuth.getInstance()
        EditText_Cadastro_Nome.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Cadastro_Email.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Cadastro_Password.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Cadastro_Password2.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)

        Button_Cadastro_Login.setOnClickListener {
            if(validateNameEmailPassword(EditText_Cadastro_Nome.text.toString(),EditText_Cadastro_Email.text.toString(),EditText_Cadastro_Password.text.toString(),EditText_Cadastro_Password2.text.toString())) {
                viewModel.cadastroUsuarioFirebase(EditText_Cadastro_Email.text.toString(), EditText_Cadastro_Password.text.toString())
            } else {
                Toast.makeText(this, "Problema nos dados informados.", Toast.LENGTH_LONG).show()
            }
        }
        initViewModel()
    }

    private fun initViewModel() {

        viewModel.stateRegister.observe(this) {
            if(it) {
                sharedPrefs.edit().putString(Constants.KEY_IDUSER, firebaseAuth.currentUser?.uid).apply()
                sharedPrefs.edit().putString(Constants.KEY_NOME, firebaseAuth.currentUser?.displayName).apply()
                sharedPrefs.edit().putString(Constants.KEY_EMAIL, firebaseAuth.currentUser?.email).apply()
                startActivity(Intent(this, GamesActivity::class.java))
            }
        }

        viewModel.loading.observe(this) {
            if(it) {
                alertDialog.show()
            } else {
                alertDialog.hide()
            }
        }
    }
    fun validateNameEmailPassword(name: String, email: String, password1: String, password2: String): Boolean {
        return when {
            name.isEmpty() || email.isEmpty() || password1.isEmpty() -> {
                false
            }
            password1!=password2 -> {
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                false
            }
            password1.length < 6 -> {
                false
            }
            else -> true
        }
    }
}