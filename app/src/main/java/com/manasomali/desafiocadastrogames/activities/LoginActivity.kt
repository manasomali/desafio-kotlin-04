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
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val viewModel: AuthViewModel by viewModels()
    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }
    lateinit var alertDialog: AlertDialog
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        firebaseAuth = FirebaseAuth.getInstance()
        EditText_Login_Email.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Login_Password.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)

        Button_Login_Login.setOnClickListener {
            if (validateNameEmailPassword(EditText_Login_Email.text.toString(),EditText_Login_Password.text.toString())) {
                viewModel.loginUsuarioFirebase(
                    EditText_Login_Email.text.toString(),
                    EditText_Login_Password.text.toString()
                )
            } else {
                Toast.makeText(this, "Informe email e senha.", Toast.LENGTH_LONG).show()
            }
        }

        Button_Login_Cadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
        initViewModel()

    }
    private fun initViewModel() {

        viewModel.stateLogin.observe(this) {
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
    fun validateNameEmailPassword(email: String, password: String): Boolean {
        return when {
            email.isEmpty() || password.isEmpty() -> {
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                false
            }
            password.length < 6 -> {
                false
            }
            else -> true
        }
    }
}