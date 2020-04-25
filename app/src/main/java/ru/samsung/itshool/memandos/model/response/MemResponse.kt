package ru.samsung.itshool.memandos.model.response

import com.google.gson.annotations.SerializedName
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.response.base.BaseResponse

data class MemResponse(
    @SerializedName("id") val id : Long?,
    @SerializedName("title") val title: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("isFavorite") val favorite: Boolean?,
    @SerializedName("createdDate") val createdDate : Long?,
    @SerializedName("photoUrl") val photoUrl : String?
) : BaseResponse<Mem> {

    override fun convert(): Mem {
        return  Mem(id ?: 0,
            title ?: "",
            description ?: "",
            favorite ?: false,
            createdDate ?: 0,
            photoUrl ?: "")
    }

}