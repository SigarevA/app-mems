package ru.samsung.itshool.memandos.ui.Activites

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

import ru.samsung.itshool.memandos.APP_PREFERENCES
import ru.samsung.itshool.memandos.NAME
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.ui.Fragments.AddingMemeFragment
import ru.samsung.itshool.memandos.ui.Fragments.ProfileFragment
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
                val intent = Intent(this, AddingMemActivity::class.java)
                startActivity(intent)
                return false
            }
            R.id.item_profile -> {
                loadFragment(ProfileFragment())
                return true
            }
        }
        return false
    }


    fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

}