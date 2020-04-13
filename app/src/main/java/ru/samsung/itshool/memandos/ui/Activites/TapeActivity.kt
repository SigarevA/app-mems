package ru.samsung.itshool.memandos.ui.Activites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import ru.samsung.itshool.memandos.APP_PREFERENCES
import ru.samsung.itshool.memandos.NAME
import ru.samsung.itshool.memandos.R

class TapeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)

        val mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        val textView = findViewById<TextView>(R.id.infoUser)
        val value = mSettings.getString(NAME, "null")
        value.let{
            textView.setText(value)
        }
    }
}
