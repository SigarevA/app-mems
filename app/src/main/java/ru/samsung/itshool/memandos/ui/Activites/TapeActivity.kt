package ru.samsung.itshool.memandos.ui.Activites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.samsung.itshool.memandos.APP_ACCESS_TOKEN

import ru.samsung.itshool.memandos.APP_PREFERENCES
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.ui.Fragments.AddingMemeFragment
import ru.samsung.itshool.memandos.ui.Fragments.RibbonFragment
import ru.samsung.itshool.memandos.utils.SharedPreferencesUtli

class TapeActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)

        init()

    }


    private fun init() {
        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        val navigation: BottomNavigationView = findViewById(R.id.navigation_main)
        navigation.setOnNavigationItemSelectedListener(::onNavigationItemSelected)
        loadFragment(RibbonFragment())
    }



    fun onNavigationItemSelected(item : MenuItem) : Boolean{
        when(item.itemId) {
            R.id.dashboard -> {
                loadFragment(RibbonFragment())
                return true
            }
            R.id.item_adding_mem -> {
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


    companion object{
        private const val TAG = "TapeActivity"
    }

}