package ru.samsung.itshool.memandos.model.repo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.DAO.MemDao

@Database(entities = arrayOf(Mem::class), version = 1)
abstract class MemDatabase : RoomDatabase() {
    abstract fun memDao(): MemDao

    companion object {

        private var INSTANCE: MemDatabase? = null

        fun getInstance(context: Context): MemDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDataBase(context).also { INSTANCE = it }
            }

        private fun buildDataBase(context: Context) =
            Room.databaseBuilder(
                context,
                MemDatabase::class.java, "database-name"
            ).build()

    }
}