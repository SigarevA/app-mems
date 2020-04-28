package ru.samsung.itshool.memandos.domain

import java.io.Serializable

data class UserInfo (
    val id : Int = 7,
    val username : String = "",
    val firstName : String = "",
    val lastName : String = "",
    val userDescription : String = ""
) : Serializable
