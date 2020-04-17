package ru.samsung.itshool.memandos.ui.VM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.samsung.itshool.memandos.domain.AuthData
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.model.repo.NetworkService
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo
import ru.samsung.itshool.memandos.model.response.AuthResponse
import java.util.function.Function

class LoginVM : ViewModel() {

    private val surfMemesRepo = SurfMemesRepo()

    private val TAG : String = LoginVM::class.java.name

    fun autheraziton(login : String, password : String ) : LiveData<Observable<AuthData>> {

        Log.d(TAG, "autheraziton")

        val resp = MutableLiveData<Observable<AuthData>>()
        resp.value = surfMemesRepo.authorize(
            AuthRequest(
                login,
                password
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.convert() }
        return resp
    }
}