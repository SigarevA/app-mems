package ru.samsung.itshool.memandos.domain

import java.io.Serializable

data class UserInfo (
    val id : Int = 7,
    val username : String = "olololo",
    val firstName : String = "Джеймс",
    val lastName : String = "Бонд",
    val userDescription : String = "Бонд, Джеймс Бонд"
) : Serializable
