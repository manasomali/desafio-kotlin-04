package com.manasomali.desafiocadastrogames.classes

import java.io.Serializable

data class Jogo(
    var id: String="",
    var nome: String="",
    var ano: String="",
    var descricao: String="",
    var capa: String=""
): Serializable