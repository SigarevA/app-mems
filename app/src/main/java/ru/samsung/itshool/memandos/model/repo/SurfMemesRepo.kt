package ru.samsung.itshool.memandos.model.repo

import io.reactivex.rxjava3.core.Observable
import ru.samsung.itshool.memandos.model.api.AndroidSchoolMemesAPI
import ru.samsung.itshool.memandos.model.response.MemResponse


private const val TAG : String = "SurfMemesRepo"

class SurfMemesRepo {

    private val memesAPI = NetworkService.retrofit.create(AndroidSchoolMemesAPI::class.java)

    fun loadMemes() : Observable<Collection<MemResponse>> {
        return memesAPI.getMems()
    }
}
