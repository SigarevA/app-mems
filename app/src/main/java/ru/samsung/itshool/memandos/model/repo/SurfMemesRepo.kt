package ru.samsung.itshool.memandos.model.repo

import android.util.Log
import io.reactivex.rxjava3.core.Observable
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.api.AndroidSchoolMemesAPI
import ru.samsung.itshool.memandos.model.response.AuthResponse
import ru.samsung.itshool.memandos.model.response.MemResponse


class SurfMemesRepo {

    private val TAG : String = SurfMemesRepo::class.java.name


    private val memesAPI = NetworkService.retrofit.create(AndroidSchoolMemesAPI::class.java)



    fun loadMemes() : Observable<Collection<MemResponse>> {
        return memesAPI.getMems()
    }

    companion object {
        private val TAG : String = "SurfMemesRepo"
    }
}
