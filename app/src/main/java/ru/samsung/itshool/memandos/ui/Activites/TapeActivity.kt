package ru.samsung.itshool.memandos.ui.Activites

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.ui.Fragments.LogoutDialogFragment
import ru.samsung.itshool.memandos.ui.Fragments.ProfileFragment
import ru.samsung.itshool.memandos.ui.Fragments.RibbonFragment
import ru.samsung.itshool.memandos.ui.VM.TapeVM
import ru.samsung.itshool.memandos.utils.SnackBarsUtil



private const val TAG = "TapeActivity"
private const val PROFILE_FRAGMENT = "profileFragment"
private const val RIBBON_FRAGMENT = "robbionFragment"
private const val CURRENT_FRAGMENT = "current"

class TapeActivity : AppCompatActivity(), LogoutDialogFragment.NoticeDialogListener  {

    private lateinit var containerFragment: FrameLayout
    private lateinit var tapeVM: TapeVM
    private lateinit var state : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)

        state = if (savedInstanceState != null)
            savedInstanceState.getString(CURRENT_FRAGMENT, "")
            else
                RIBBON_FRAGMENT

        tapeVM = ViewModelProvider(this).get(TapeVM::class.java)

        init()
    }

    private fun init() {
        containerFragment = findViewById(R.id.container)
        val navigation: BottomNavigationView = findViewById(R.id.navigation_main)
        navigation.setOnNavigationItemSelectedListener(::onNavigationItemSelected)
        when (state) {
            PROFILE_FRAGMENT -> loadFragment(ProfileFragment(), PROFILE_FRAGMENT)
            RIBBON_FRAGMENT -> loadFragment(RibbonFragment(), RIBBON_FRAGMENT)
        }
    }


    fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.dashboard -> {
                loadFragment(RibbonFragment(), RIBBON_FRAGMENT)
                return true
            }
            R.id.item_adding_mem -> {
                val intent = Intent(this, AddingMemActivity::class.java)
                startActivityForResult(intent, 1)
                return false
            }
            R.id.item_profile -> {
                loadFragment(ProfileFragment(), PROFILE_FRAGMENT)
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

    override fun onSaveInstanceState(outState: Bundle) {

        outState.run{
            Log.d(TAG, "save, state : ${state}")
            putString(CURRENT_FRAGMENT, state)
        }
        super.onSaveInstanceState(outState)
    }

    fun loadFragment(fragment: Fragment, tagFragment: String) {
        val ft = supportFragmentManager.beginTransaction()
        state = tagFragment
        ft.replace(R.id.container, fragment, tagFragment)
        ft.commit()
    }

    override fun onDialogPositiveClick() {
        tapeVM.logout().observe(this, Observer {
            when {
                it.isSuccess -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                it.isFailure -> {
                    SnackBarsUtil.errorSnackBar("Выйти не удалось", containerFragment)
                }
            }
        })
    }
}