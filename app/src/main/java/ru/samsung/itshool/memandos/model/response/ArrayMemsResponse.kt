package ru.samsung.itshool.memandos.model.response

import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.response.base.BaseResponse
import java.util.*

data class ArrayMemsResponse (
    val mems : Array<MemResponse>
) : BaseResponse<Collection<Mem>>
{
    override fun convert(): Collection<Mem> {
        val memes = LinkedList<Mem>()
        mems.forEach { memes.add(it.convert()) }
        return memes
    }
}