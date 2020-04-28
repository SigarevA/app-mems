package ru.samsung.itshool.memandos.ui.Fragments

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


open class LogoutDialogFragment(val mListener: NoticeDialogListener) : DialogFragment(){



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val adb = AlertDialog.Builder(it)
                .setTitle("Действительно хотите выйти ? ")
                .setPositiveButton("ВЫЙТИ") { dialog, _ ->
                    mListener.onDialogPositiveClick()
                    dialog.cancel()
                }
                .setNegativeButton("ОТМЕНА") { dialog, _ ->
                    dialog.cancel()
                }
            return  adb.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "")
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick()
    }


    companion object {
        private const val TAG = "LogoutDialogFragment"
    }
}
