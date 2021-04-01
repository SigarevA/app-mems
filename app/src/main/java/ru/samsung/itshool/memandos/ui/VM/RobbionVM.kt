package ru.samsung.itshool.memandos.ui.VM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.SingleLiveEvent
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo
import java.util.*
import javax.inject.Inject

class RobbionVM : ViewModel() {

    @Inject
    lateinit var surfMemesRepo: SurfMemesRepo
    private val memMutableLiveData = MutableLiveData<List<Mem>>()
    private val compositeDisposable = CompositeDisposable()
    val eventLiveData = SingleLiveEvent<String>()
    val memLiveData: LiveData<List<Mem>>
        get() = memMutableLiveData

    /*
    TODO move surfMemesRepo to constructor
    init {
        getMemes()
    }
     */

    fun searchQuery() {
    }

    fun refreshMemes() {
        getMemes()
    }

    fun getMemes() {
        val dispose = loadMemes()
            .subscribe(
                { memMutableLiveData.value = it },
                { eventLiveData.value = it.message }
            )
        compositeDisposable.add(dispose)
    }

    private fun loadMemes(): Observable<List<Mem>> {
        return surfMemesRepo.loadMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val res = LinkedList<Mem>()
                it.forEach { res.add(it.convert()) }
                Log.d(TAG, String.format("size : %d", res.size))
                res
            }
    }

    companion object {
        private val TAG = RobbionVM::class.java.name
    }
}