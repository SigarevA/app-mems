package ru.samsung.itshool.memandos.model.repo

import androidx.lifecycle.MutableLiveData
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

typealias ResponseResult = Result<ResponseAuthorization>

class SurfMemesRepo {


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val androidSchoolAPI = retrofit.create(AndroidSchoolAPI::class.java)


    fun authorize ( requestLogin: RequestLogin) : MutableLiveData<ResponseResult>{

        val resp = MutableLiveData<ResponseResult>()

        androidSchoolAPI
            .authorizate(requestLogin )
            .enqueue(
                object : Callback<ResponseLogin> {
                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        val body = response.body()
                        body?.let {
                            resp.value = Result.success(body.convert())
                        }
                    }

                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                        resp.value = Result.failure(t)
                    }
                }
            )
        return resp
    }


}
