package ru.samsung.itshool.memandos.ui.Activites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar

import ru.samsung.itshool.memandos.APP_PREFERENCES
import ru.samsung.itshool.memandos.NAME
import ru.samsung.itshool.memandos.R

class TapeActivity : AppCompatActivity() {

    lateinit var toolbar : Toolbar


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return true
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)




        val mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    }
}
