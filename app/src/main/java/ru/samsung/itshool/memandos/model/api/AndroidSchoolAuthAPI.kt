package ru.samsung.itshool.memandos.model.api


import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.response.AuthResponse

interface AndroidSchoolAuthAPI {
    @POST("auth/login")
    fun authorizate(@Body data : AuthRequest) : Observable<AuthResponse>

    @POST("/auth/logout")
    fun logout() : Completable
}