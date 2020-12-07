package ru.samsung.itshool.memandos.model.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import ru.samsung.itshool.memandos.model.response.MemResponse

interface AndroidSchoolMemesAPI {
    @GET("memes")
    fun getMems() : Observable<Collection<MemResponse>>
}