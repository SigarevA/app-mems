package ru.samsung.itshool.memandos.ui.VM

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import ru.samsung.itshool.memandos.ui.Activites.AddingMemActivity
import java.util.*

private const val TAG = "AddingMemVM"

class AddingMemVM(application: Application) : AndroidViewModel(application) {

    private val memDataBase : MemDatabase = MemDatabase.getInstance(getApplication())



    fun saveMem(mem : Mem) : LiveData<Result<Unit>> {

        val resp = MutableLiveData<Result<Unit>>()

        memDataBase.memDao().insert(mem)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe (
                {
                    resp.value =Result.success(Unit)
                    Log.d(TAG, "success")
                },
                {
                    resp.value = Result.failure(it)
                }
            )

        return resp
    }

}