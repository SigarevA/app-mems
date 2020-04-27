package ru.samsung.itshool.memandos.utils

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout

import ru.samsung.itshool.memandos.R

class SnackBarsUtil {

    companion object {

        @SuppressLint("ResourceAsColor")
        fun errorSnackBar(text : String, v : View) {
            val mSnackBar =  Snackbar.make(v, text, Snackbar.LENGTH_SHORT)
            mSnackBar.setBackgroundTint(Color.RED)
            mSnackBar.setTextColor(Color.WHITE)
            mSnackBar.show()
        }

    }

}