package ru.samsung.itshool.memandos.model.api

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.POST
import retrofit2.Call;
import retrofit2.http.Body
import ru.samsung.itshool.memandos.model.response.RequestLogin
import ru.samsung.itshool.memandos.model.response.ResponseAuthorization
import ru.samsung.itshool.memandos.model.response.ResponseLogin

interface AndroidSchoolAPI {

    @POST("auth/login")
    fun authorizate(@Body data : RequestLogin) : Observable<ResponseAuthorization>
}