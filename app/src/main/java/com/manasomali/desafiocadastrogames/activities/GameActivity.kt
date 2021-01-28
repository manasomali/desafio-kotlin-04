package com.manasomali.desafiocadastrogames.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.classes.Jogo
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.activity_add_game.*
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Button_Game_Edit.bringToFront()
        var gameClick = intent.getSerializableExtra("gameClick") as Jogo
        TextView_Game_Lancamento.text = gameClick.ano
        TextView_Game_Descricao.text = gameClick.descricao
        TextView_Game_Titulo1.text = gameClick.nome
        TextView_Game_Titulo2.text = gameClick.nome
        Picasso.get().load(Uri.parse(gameClick.capa)).placeholder(R.drawable.ic_camera).into(ImageView_Game_Capa)

//        val bitmap = (ImageView_Game_Capa.drawable as BitmapDrawable).bitmap
//        Palette.from(bitmap).maximumColorCount(32).generate { palette ->
//            var darkVibrantSwatch = palette!!.darkVibrantSwatch!!
//            ImageView_Game_Capa.setBackgroundColor(darkVibrantSwatch.rgb)
//        }
        Button_Game_Edit.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            intent.putExtra("gameClick", gameClick)
            startActivity(intent)
        }
        Button_Game_Voltar.setOnClickListener {
            val intent = Intent(this, GamesActivity::class.java)
            startActivity(intent)
        }

    }


}