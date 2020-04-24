package ru.samsung.itshool.memandos.domain

data class Mem(
    val id : Long = 0,
    val title : String = "mem",
    val description : String = "null mem",
    val isFavorite : Boolean = false,
    val createdDate : Int?,
    val photoUrl : String?
)