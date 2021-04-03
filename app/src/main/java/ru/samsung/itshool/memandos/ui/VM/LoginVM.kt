package ru.samsung.itshool.memandos.ui.VM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.model.repo.SurfAuthorizationRepo
import ru.samsung.itshool.memandos.model.response.AuthRequest
import ru.samsung.itshool.memandos.storage.UserStorageSharedPref

private const val TAG = "LoginVM"

class LoginVM(
    private val surfAuthRepo: SurfAuthorizationRepo,
    private val userStorageSharedPref: UserStorageSharedPref
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    fun authorize(login: String, password: String): LiveData<Result<Unit>> {
        val authLiveData = MutableLiveData<Result<Unit>>()
        val dispose = surfAuthRepo.authorize(
            AuthRequest(
                login,
                password
            )
        )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.convert() }
            .subscribe(
                {
                    userStorageSharedPref.saveUserData(it.userInfo)
                    authLiveData.value = Result.success(Unit)
                },
                {
                    authLiveData.value = Result.failure(it)
                }
            )
        compositeDisposable.add(dispose)
        return authLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}