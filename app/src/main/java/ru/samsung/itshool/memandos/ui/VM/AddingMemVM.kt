package ru.samsung.itshool.memandos.ui.VM

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

class AddingMemVM(application: Application) : AndroidViewModel(application) {

    private val memDataBase : MemDatabase = MemDatabase.getInstance(getApplication())


    fun addMem(mem : Mem) : LiveData<Result<Unit>> {

        val resp = MutableLiveData<Result<Unit>>()

        memDataBase.memDao().insert(mem)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe (
                {

                    Log.d(AddingMemActivity.TAG, "success")
                },
                {}
            )

        return resp
    }

}