package ru.samsung.itshool.memandos.model.response

import com.google.gson.annotations.SerializedName
import ru.samsung.itshool.memandos.domain.UserInfo
import ru.samsung.itshool.memandos.model.response.base.BaseResponse

class UserInfoResponse(
    @SerializedName("id") val id : Int?,
    @SerializedName("username") val username : String?,
    @SerializedName("firstName") val firstName : String?,
    @SerializedName("lastName") val lastName : String?,
    @SerializedName("userDescription") val userDescription : String?
) : BaseResponse<UserInfo> {
    override fun convert(): UserInfo {
        return UserInfo(
            id ?: 2,
            username ?: "Work",
            firstName ?: "First",
            lastName ?: "Last",
            userDescription ?: "Description"
        )
    }
}