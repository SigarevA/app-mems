package ru.samsung.itshool.memandos.model.repo

import androidx.lifecycle.MutableLiveData
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.samsung.itshool.memandos.model.BASE_URL
import ru.samsung.itshool.memandos.model.response.RequestLogin
import ru.samsung.itshool.memandos.model.response.ResponseLogin
import ru.samsung.itshool.memandos.model.api.AndroidSchoolAPI
import ru.samsung.itshool.memandos.model.response.ResponseAuthorization



class SurfMemesRepo {


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val androidSchoolAPI = retrofit.create(AndroidSchoolAPI::class.java)


    fun authorize ( requestLogin: RequestLogin) : Observable<ResponseAuthorization> {

        return androidSchoolAPI
                    .authorizate(requestLogin)
    }


}
