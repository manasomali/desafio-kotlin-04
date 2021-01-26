package com.manasomali.desafiocadastrogames.activities

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manasomali.desafiocadastrogames.R
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        EditText_Cadastro_Nome.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Cadastro_Email.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Cadastro_Password.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_Cadastro_Password2.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)

        Button_Cadastro_Login.setOnClickListener {
            startActivity(Intent(this, GamesActivity::class.java))
        }
    }
}