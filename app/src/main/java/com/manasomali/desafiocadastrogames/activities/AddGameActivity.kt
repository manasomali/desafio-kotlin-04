package com.manasomali.desafiocadastrogames.activities

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.manasomali.desafiocadastrogames.helpers.Constants.EMPTY_STRING
import com.manasomali.desafiocadastrogames.helpers.Constants.KEY_EMAIL
import com.manasomali.desafiocadastrogames.helpers.Constants.PREFS_NAME
import com.manasomali.desafiocadastrogames.R
import com.manasomali.desafiocadastrogames.viewmodels.GamesViewModel
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_add_game.*
import com.manasomali.desafiocadastrogames.classes.Jogo as Jogo

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddGameActivity : AppCompatActivity() {
    val sharedPrefs by lazy {  getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    private val viewModel: GamesViewModel by viewModels()
    private lateinit var alertDialog: AlertDialog
    lateinit var storageReference: StorageReference
    private val COD_IMG = 1000
    var capaUri: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_game)

        var gameClick = intent.getSerializableExtra("gameClick") as Jogo
            EditText_AddGame_Ano.setText(gameClick.ano)
            EditText_AddGame_Descricao.setText(gameClick.descricao)
            EditText_AddGame_Nome.setText(gameClick.nome)
            Picasso.get().load(Uri.parse(gameClick.capa)).placeholder(R.drawable.ic_camera).into(CircularImageView_AddGame_Capa)
            capaUri = gameClick.capa
        if(gameClick.id != "") {
            CircularImageView_AddGame_Delete.visibility = VISIBLE
        }
        CircularImageView_AddGame_Delete.setOnClickListener {
            viewModel.delGame(sharedPrefs.getString(KEY_EMAIL, EMPTY_STRING).toString(),gameClick.id)
        }
        EditText_AddGame_Ano.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_AddGame_Descricao.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)
        EditText_AddGame_Nome.background.setColorFilter(getColor(R.color.grey), PorterDuff.Mode.SRC_IN)

        Button_AddGame_SaveGame.setOnClickListener {
            if(validateFildsGame(
                    EditText_AddGame_Nome.text.toString(),
                    EditText_AddGame_Ano.text.toString(),
                    EditText_AddGame_Descricao.text.toString())
            ) {
                if (gameClick.id=="") {
                    viewModel.addGame(EditText_AddGame_Nome.text.toString(),
                        EditText_AddGame_Ano.text.toString(),
                        EditText_AddGame_Descricao.text.toString(),
                        capaUri,
                        sharedPrefs.getString(KEY_EMAIL, EMPTY_STRING).toString())
                } else {
                    viewModel.editGame(gameClick.id,EditText_AddGame_Nome.text.toString(),
                        EditText_AddGame_Ano.text.toString(),
                        EditText_AddGame_Descricao.text.toString(),
                        capaUri,
                        sharedPrefs.getString(KEY_EMAIL, EMPTY_STRING).toString())
                }
            } else {
                Toast.makeText(this, "Informe os dados para cadastro.", Toast.LENGTH_SHORT).show()
            }
        }
        CircularImageView_AddGame_Capa.setOnClickListener {
            setImgFirebase()
        }
        alertDialog = SpotsDialog.Builder().setContext(this).build()
        initViewModel()
    }
    fun setImgFirebase() {
        if(EditText_AddGame_Nome.text.toString().isNotBlank()) {
            var rootFolder = sharedPrefs.getString(KEY_EMAIL, EMPTY_STRING).toString()
            storageReference = FirebaseStorage.getInstance().getReference( rootFolder+ "/gamesPics/" + EditText_AddGame_Nome.text.toString())
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            alertDialog.show()
            startActivityForResult(Intent.createChooser(intent, "Selecione a Imagem"), COD_IMG)
        } else {
            Toast.makeText(this, "Informe o nome do Game.", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == COD_IMG){
            val uploadFile = storageReference.putFile(data!!.data!!)
            uploadFile.continueWithTask{storageReference.downloadUrl}.addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Toast.makeText(this, "Imagem Carrregada com sucesso!", Toast.LENGTH_SHORT).show()
                    capaUri = task.result.toString()
                    Picasso.get().load(Uri.parse(capaUri)).placeholder(R.drawable.ic_camera).into(CircularImageView_AddGame_Capa)
                }
                alertDialog.hide()
            }
        }
    }
    private fun initViewModel() {
        viewModel.stateAddGame.observe(this) {
            if(it) {
                Toast.makeText(this, "Jogo cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, GamesActivity::class.java))
            } else {
                Toast.makeText(this, "Falha no cadastro do jogo.", Toast.LENGTH_SHORT).show()
            }
        }
        viewModel.stateDelGame.observe(this) {
            if(it) {
                Toast.makeText(this, "Jogo deletado com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, GamesActivity::class.java))
            } else {
                Toast.makeText(this, "Falha na deleção do jogo.", Toast.LENGTH_SHORT).show()
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
    fun validateFildsGame(name: String, ano: String, descricao: String): Boolean {
        return when {
            name.isBlank() || ano.isBlank() || descricao.isBlank() -> {
                false
            }
            else -> true
        }
    }
}