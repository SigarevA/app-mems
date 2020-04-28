package ru.samsung.itshool.memandos.model.repo

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import ru.samsung.itshool.memandos.model.api.AndroidSchoolAuthAPI
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.response.AuthResponse

class SurfAuthorizationRepo {


    private val authApi = NetworkService.retrofit.create(AndroidSchoolAuthAPI::class.java)


    fun authorize ( requestLogin: AuthRequest) : Observable<AuthResponse> {

        Log.d(TAG, "method authorize")

        return authApi
            .authorizate(requestLogin)
    }

    companion object {
        private const val TAG = "SurfAuthorizationRepo"
    }
}