package com.manasomali.desafiocadastrogames

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.palette.graphics.Palette.Swatch
import kotlinx.android.synthetic.main.activity_game.*


class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        Button_Game_Edit.bringToFront()

        val bitmap = (ImageView_Capa.drawable as BitmapDrawable).bitmap
        Palette.from(bitmap).maximumColorCount(32).generate { palette ->
            var darkVibrantSwatch = palette!!.darkVibrantSwatch!!
            ImageView_Capa.setBackgroundColor(darkVibrantSwatch.rgb)
        }
    }
}