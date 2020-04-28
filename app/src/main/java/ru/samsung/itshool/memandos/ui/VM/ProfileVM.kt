package ru.samsung.itshool.memandos.ui.VM

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import ru.samsung.itshool.memandos.model.repo.NetworkService
import ru.samsung.itshool.memandos.model.repo.SurfMemesRepo

class ProfileVM : ViewModel() {

    private val surfMemesRepo : SurfMemesRepo = SurfMemesRepo()


    fun logout() : LiveData<Result<Unit>>{

        val resp = MutableLiveData<Result<Unit>>()

        surfMemesRepo.logout()
            .subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io())
            .observeOn(io.reactivex.rxjava3.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe({
                resp.value = Result.success(Unit)
            }
            ,{
                resp.value = Result.failure(it)
                Log.d(TAG, "error : ${it}")
            })

        return resp
    }


    fun getMyMemes(context: Context) : LiveData<List<Mem>> {

        val memes = MutableLiveData<List<Mem>>()

        val mDataBase = MemDatabase.getInstance(context)
        mDataBase.memDao().getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    memes.value = it
                },
                {
                    Log.d(TAG, it.message)
                }
            )

        return memes
    }

    companion object {
        private val TAG = RobbionVM::class.java.name
    }
}