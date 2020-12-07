package ru.samsung.itshool.memandos.model.repo

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import ru.samsung.itshool.memandos.model.api.AndroidSchoolAuthAPI
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.response.AuthResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SurfAuthorizationRepo @Inject constructor() {

    @Inject
    lateinit var authApi: AndroidSchoolAuthAPI

    fun authorize(requestLogin: AuthRequest): Observable<AuthResponse> {
        return authApi.authorizate(requestLogin)
    }

    fun logout(): Completable {
        return authApi.logout()
    }
}