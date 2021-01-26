package com.manasomali.desafiocadastrogames.activities

import android.content.Intent
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.manasomali.desafiocadastrogames.R
import kotlinx.android.synthetic.main.activity_add_game.*
import kotlinx.android.synthetic.main.activity_game.*

class AddGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        EditText_AddGame_Ano.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_AddGame_Descricao.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_AddGame_Nome.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)

        Button_Game_Voltar.setOnClickListener {
            startActivity(Intent(this, GamesActivity::class.java))
        }
        Button_AddGame_SaveGame.setOnClickListener {
            startActivity(Intent(this, GamesActivity::class.java))
        }
        Button_Game_Edit.setOnClickListener {

        }
    }
}