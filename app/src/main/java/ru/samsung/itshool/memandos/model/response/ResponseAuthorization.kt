package ru.samsung.itshool.memandos.model.response

import ru.samsung.itshool.memandos.domain.UserInfo

class ResponseAuthorization (
    val accessToken : String,
    val userInfo : UserInfo
) {
}