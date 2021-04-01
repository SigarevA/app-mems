package ru.samsung.itshool.memandos.ui.VM

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.SingleLiveEvent
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import javax.inject.Inject

private const val TAG = "AddingMemVM"

sealed class AddingMemResult {
    object SuccessAddingResult : AddingMemResult()
}

class AddingMemVM(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable = CompositeDisposable()
    private val eventSingleLiveEvent : SingleLiveEvent<AddingMemResult> = SingleLiveEvent()

    val eventLiveData : LiveData<AddingMemResult>
        get() = eventSingleLiveEvent

    @Inject
    lateinit var memDataBase: MemDatabase

    fun saveMem(mem: Mem): LiveData<Result<Unit>> {
        val resp = MutableLiveData<Result<Unit>>()
        Log.d(TAG, "save mem")
        val dispose = memDataBase.memDao().insert(mem)
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe(
                {
                    resp.value = Result.success(Unit)

                },
                {
                    resp.value = Result.failure(it)
                }
            )
        compositeDisposable.add(dispose)
        return resp
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}