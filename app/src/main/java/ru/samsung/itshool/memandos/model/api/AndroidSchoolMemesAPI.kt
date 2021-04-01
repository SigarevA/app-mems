package ru.samsung.itshool.memandos.model.api

import io.reactivex.Observable
import retrofit2.http.GET
import ru.samsung.itshool.memandos.model.response.MemResponse

interface AndroidSchoolMemesAPI {
    @GET("memes")
    fun getMems() : Observable<Collection<MemResponse>>
}