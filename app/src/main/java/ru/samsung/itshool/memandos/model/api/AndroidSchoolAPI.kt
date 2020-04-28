package ru.samsung.itshool.memandos.model.api

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.GET
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.response.AuthResponse
import ru.samsung.itshool.memandos.model.response.MemResponse

interface AndroidSchoolAPI {

    @POST("auth/login")
    fun authorizate(@Body data : AuthRequest) : Observable<AuthResponse>


    @GET("memes")
    fun getMems() : Observable<Collection<MemResponse>>

    @POST("/auth/logout")
    fun logout() : Completable
}