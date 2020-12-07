package ru.samsung.itshool.memandos.ui.VM

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import javax.inject.Inject

private const val TAG = "AddingMemVM"

class AddingMemVM(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var memDataBase: MemDatabase

    fun saveMem(mem: Mem): LiveData<Result<Unit>> {

        Log.d(TAG, "save mem")
        val resp = MutableLiveData<Result<Unit>>()
        Log.d(TAG, "save mem")
        memDataBase.memDao().insert(mem)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe(
                {
                    resp.value = Result.success(Unit)
                    Log.d(TAG, "success")
                },
                {
                    Log.d(TAG, "failure")
                    resp.value = Result.failure(it)
                }
            )
        return resp
    }
}