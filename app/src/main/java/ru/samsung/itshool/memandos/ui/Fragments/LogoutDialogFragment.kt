package ru.samsung.itshool.memandos.ui.Fragments

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

private const val TAG = "LogoutDialogFragment"

open class LogoutDialogFragment() : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val adb = AlertDialog.Builder(it)
                .setTitle("Действительно хотите выйти ? ")
                .setPositiveButton("ВЫЙТИ") { dialog, _ ->
                    listener.onDialogPositiveClick()
                    dialog.cancel()
                }
                .setNegativeButton("ОТМЕНА") { dialog, _ ->
                    dialog.cancel()
                }
            return adb.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "")
    }

    interface NoticeDialogListener {
        fun onDialogPositiveClick()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(
                (context.toString() +
                        " must implement NoticeDialogListener")
            )
        }
    }
}