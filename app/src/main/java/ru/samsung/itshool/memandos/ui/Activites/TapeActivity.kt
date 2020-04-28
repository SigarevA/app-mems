package ru.samsung.itshool.memandos.ui.Activites

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.ui.Fragments.ProfileFragment
import ru.samsung.itshool.memandos.ui.Fragments.RibbonFragment
import ru.samsung.itshool.memandos.utils.SnackBarsUtil


private const val TAG = "TapeActivity"

class TapeActivity : AppCompatActivity() {

    private lateinit var containerFragment: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)

        init()
    }


    private fun init() {
        containerFragment = findViewById(R.id.container)
        val navigation: BottomNavigationView = findViewById(R.id.navigation_main)
        navigation.setOnNavigationItemSelectedListener(::onNavigationItemSelected)
        loadFragment(RibbonFragment())
    }


    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dashboard -> {
                loadFragment(RibbonFragment())
                return true
            }
            R.id.item_adding_mem -> {
                val intent = Intent(this, AddingMemActivity::class.java)
                startActivityForResult(intent, 1)
                return false
            }
            R.id.item_profile -> {
                loadFragment(ProfileFragment())
                return true
            }
        }
        return false
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, "debug result")
        when (requestCode) {
            1 -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.let {
                        if (AddingMemActivity.getResult(data))
                            SnackBarsUtil.successSnackBar("Мем добавлен!", containerFragment)
                        else
                            SnackBarsUtil.errorSnackBar("Мем не добавлен!", containerFragment)
                    }
                }
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.container, fragment)
        ft.commit()
    }

}