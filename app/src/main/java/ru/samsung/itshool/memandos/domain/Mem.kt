package ru.samsung.itshool.memandos.domain

data class Mem(
    val id : Long = 0,
    val title : String = "",
    val description : String = "",
    val isFavorite : Boolean = false,
    val createdDate : Int?,
    val photoUrl : String?
)