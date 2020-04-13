package ru.samsung.itshool.memandos.model.response.base

interface BaseResponse <T> {
    fun convert(): T
}