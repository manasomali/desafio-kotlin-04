package com.manasomali.desafiocadastrogames.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.manasomali.desafiocadastrogames.helpers.Constants
import com.manasomali.desafiocadastrogames.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    val scope = CoroutineScope(Dispatchers.Main)
    val sharedPrefs by lazy {  getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var intent = Intent(this, LoginActivity::class.java)
        val user = Firebase.auth.currentUser
        if (user != null) {
            for (user in FirebaseAuth.getInstance().currentUser!!.providerData) {
                if (user.providerId == "password") {
                    Toast.makeText(this, "Sign In usando email e password", Toast.LENGTH_SHORT).show()
                    sharedPrefs.edit().putString(Constants.KEY_IDUSER, user?.uid).apply()
                    sharedPrefs.edit().putString(Constants.KEY_NOME, user?.displayName).apply()
                    sharedPrefs.edit().putString(Constants.KEY_EMAIL, user?.email).apply()
                }
            }
            intent = Intent(this, GamesActivity::class.java)
        }
        scope.launch {
            delay(1000)
            startActivity(intent)
            finish()
        }
    }
}