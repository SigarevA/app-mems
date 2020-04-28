package ru.samsung.itshool.memandos.ui.VM

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.DiffUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo
import ru.samsung.itshool.memandos.ui.adapters.MemesDiffCallback
import io.reactivex.rxjava3.core.Observable
import java.util.*

class RobbionVM : ViewModel() {

    private val surfMemesRepo by lazy {SurfMemesRepo()}


    private var _memes : List<Mem> = Collections.emptyList()


    fun refreshMemes() : LiveData<Result<DiffUtil.DiffResult>> {
        val  resultRefresh =  MutableLiveData<Result<DiffUtil.DiffResult>>()
        Log.d(TAG, "Refresh memes!")
        loadMemes()
            .map {
                Log.d(TAG, "Refresh complete!")
                DiffUtil.calculateDiff(MemesDiffCallback( it, _memes ))
            }
            .subscribe(
                { resultRefresh.value = Result.success(it)},
                { resultRefresh.value = Result.failure(it)}
            )
        return resultRefresh
    }


    fun getMemes() : LiveData<Result<List<Mem>>> {

        val memesResponse = MutableLiveData<Result<List<Mem>>>()

        Log.d(TAG,"Loading memes")
        loadMemes()
            .subscribe(
                {
                    _memes = it
                    memesResponse.value = Result.success(it)
                    Log.d(TAG, memesResponse.value.toString())
                },
                {
                    memesResponse.value = Result.failure(it)
                    Log.d(TAG, it.message)
                }
            )
        return memesResponse
    }


    private fun loadMemes() : Observable<List<Mem>> {
        return surfMemesRepo.loadMemes()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                val res = LinkedList<Mem>()
                it.forEach{res.add(it.convert())}
                Log.d(TAG, String.format("size : %d", res.size))
                res
            }
    }

    companion object {
        private val TAG = RobbionVM::class.java.name
    }
}