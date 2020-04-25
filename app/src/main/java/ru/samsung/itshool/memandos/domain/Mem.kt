package ru.samsung.itshool.memandos.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Mem(
    @PrimaryKey val id : Long = 0,
    @ColumnInfo(name = "mem_title") val title : String = "mem",
    @ColumnInfo(name = "mem_description")  val description : String = "null mem",
    @ColumnInfo(name = "mem_isFavorite" ) val isFavorite : Boolean = false,
    @ColumnInfo(name = "mem_createdDate") val createdDate : Long?,
    @ColumnInfo(name = "mem_photoUrl") val photoUrl : String?   
)