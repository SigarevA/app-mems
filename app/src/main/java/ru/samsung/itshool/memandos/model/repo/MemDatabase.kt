package ru.samsung.itshool.memandos.model.repo

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.DAO.MemDao

@Database(entities = arrayOf(Mem::class), version = 1)
abstract class MemDatabase : RoomDatabase() {
    abstract fun memDao(): MemDao
}