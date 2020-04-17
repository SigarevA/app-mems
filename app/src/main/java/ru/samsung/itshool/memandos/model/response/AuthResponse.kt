package ru.samsung.itshool.memandos.model.response

import com.google.gson.annotations.SerializedName
import ru.samsung.itshool.memandos.domain.AuthData
import ru.samsung.itshool.memandos.domain.UserInfo
import ru.samsung.itshool.memandos.model.response.base.BaseResponse

class AuthResponse (
    @SerializedName("accessToken") val accessToken : String?,
    @SerializedName("userInfo") val user : UserInfoResponse?
) : BaseResponse<AuthData> {

    override fun convert(): AuthData {
        return AuthData(accessToken ?: "" ,
         user?.convert() ?: UserInfo() )
    }
}