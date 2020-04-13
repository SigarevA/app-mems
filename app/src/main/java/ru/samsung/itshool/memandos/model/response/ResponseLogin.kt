package ru.samsung.itshool.memandos.model.response

import com.google.gson.annotations.SerializedName
import ru.samsung.itshool.memandos.domain.UserInfo
import ru.samsung.itshool.memandos.model.response.base.BaseResponse

class ResponseLogin (
    @SerializedName("accessToken") val accessToken : String?,
    @SerializedName("userInfo") val user : UserInfoResponse?
) : BaseResponse<ResponseAuthorization> {

    override fun convert(): ResponseAuthorization {
        return ResponseAuthorization(accessToken ?: "" ,
         user?.convert() ?: UserInfo() )
    }
}