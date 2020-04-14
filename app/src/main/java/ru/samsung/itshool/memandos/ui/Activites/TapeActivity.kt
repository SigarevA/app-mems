package ru.samsung.itshool.memandos.ui.Activites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

import ru.samsung.itshool.memandos.APP_PREFERENCES
import ru.samsung.itshool.memandos.NAME
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.ui.Fragments.AddingMemeFragment
import ru.samsung.itshool.memandos.ui.Fragments.RibbonFragment

class TapeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)

        init()


        val mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

    }


    private fun init() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        val navigation: BottomNavigationView = findViewById(R.id.navigation_main)
        navigation.setOnNavigationItemSelectedListener(::onNavigationItemSelected)
        loadFragment(AddingMemeFragment())
    }



    fun onNavigationItemSelected(item : MenuItem) : Boolean{
        when(item.itemId) {
            R.id.dashboard -> {
                loadFragment(AddingMemeFragment())
                return true
            }
        }

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.main_menu, menu)
        return true
    }


    fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

}