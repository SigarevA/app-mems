package ru.samsung.itshool.memandos.model.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

import ru.samsung.itshool.memandos.domain.Mem

@Dao
interface MemDao {
    @Query("SELECT * FROM memes")
    fun getAll(): Flowable<List<Mem>>

    @Query("SELECT * FROM memes WHERE mem_id IN (:userIds)")
    fun loadAllByIds(userIds: Long): Flowable<List<Mem>>

    @Insert
    fun insert(mem: Mem): Single<Long>

    @Delete
    fun delete(mem: Mem)
}