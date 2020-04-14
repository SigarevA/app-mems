package ru.samsung.itshool.memandos.ui.VM

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import ru.samsung.itshool.memandos.domain.UserInfo
import ru.samsung.itshool.memandos.model.response.RequestLogin
import ru.samsung.itshool.memandos.model.repo.NetworkService
import ru.samsung.itshool.memandos.model.response.ResponseAuthorization

class LoginVM : ViewModel() {

    private val surfMemesRepo = NetworkService.surfMemesRepo



    fun autheraziton(login : String, password : String ) : LiveData<Observable<ResponseAuthorization>> {

        val resp = MutableLiveData<Observable<ResponseAuthorization>>()
        resp.value = surfMemesRepo.authorize(
            RequestLogin(
                login,
                password
            )
        )

        return resp
    }
}