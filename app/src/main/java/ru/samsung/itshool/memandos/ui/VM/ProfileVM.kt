package ru.samsung.itshool.memandos.ui.VM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo
import javax.inject.Inject

class ProfileVM : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var surfMemesRepo: SurfMemesRepo

    @Inject
    lateinit var memDatabase: MemDatabase

    fun getMyMemes(): LiveData<List<Mem>> {
        val memesLiveData = MutableLiveData<List<Mem>>()
        val dispose = memDatabase.memDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    memesLiveData.value = it
                },
                {
                    Log.d(TAG, it.message)
                }
            )
        compositeDisposable.add(dispose)
        return memesLiveData
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    companion object {
        private val TAG = RobbionVM::class.java.name
    }
}