package ru.samsung.itshool.memandos.model.repo

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.api.AndroidSchoolAPI
import ru.samsung.itshool.memandos.model.response.AuthResponse


class SurfMemesRepo {

    private val TAG : String = SurfMemesRepo::class.java.name


    private val androidSchoolAPI = NetworkService.retrofit.create(AndroidSchoolAPI::class.java)


    fun authorize ( requestLogin: AuthRequest) : Observable<AuthResponse> {

        Log.d(TAG, "method authorize")

        return androidSchoolAPI
                    .authorizate(requestLogin)
    }


}
