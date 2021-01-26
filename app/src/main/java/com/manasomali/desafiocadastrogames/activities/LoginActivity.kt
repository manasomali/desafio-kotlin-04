package com.manasomali.desafiocadastrogames.activities

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manasomali.desafiocadastrogames.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        EditText_Login_Email.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Login_Password.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)

        Button_Login_Login.setOnClickListener {
            startActivity(Intent(this, GamesActivity::class.java))
        }

        Button_Login_Cadastro.setOnClickListener {
            startActivity(Intent(this, CadastroActivity::class.java))
        }
    }
}