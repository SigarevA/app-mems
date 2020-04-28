package ru.samsung.itshool.memandos.ui.Fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

open class DialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder
                .setTitle("Загрузка картинки!")
                .setMessage("Выберите ресур")
                .setPositiveButton("Галерея") { dialog, id ->
                    dialog.cancel()
                }
                .setNegativeButton("Камера") { dialog, i ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}