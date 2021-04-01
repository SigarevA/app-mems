package ru.samsung.itshool.memandos.ui.Activites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.databinding.ActivityTapeBinding
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.ui.Fragments.LogoutDialogFragment
import ru.samsung.itshool.memandos.ui.Fragments.TabContainerFragment
import ru.samsung.itshool.memandos.ui.Screens.Tab
import ru.samsung.itshool.memandos.ui.VM.TapeVM
import ru.samsung.itshool.memandos.ui.common.BackButtonListener
import ru.samsung.itshool.memandos.utils.SnackBarsUtil

private const val TAG = "TapeActivity"
private const val PROFILE_FRAGMENT = "profileFragment"
private const val RIBBON_FRAGMENT = "robbionFragment"
private const val CURRENT_FRAGMENT = "current"

class TapeActivity : AppCompatActivity(), LogoutDialogFragment.NoticeDialogListener {

    private lateinit var containerFragment: FrameLayout
    private lateinit var tapeVM: TapeVM
    private lateinit var state: String

    private val binding by viewBinding(ActivityTapeBinding::bind, R.id.tape_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tape)
        tapeVM = ViewModelProvider(this).get(TapeVM::class.java)
        ComponentHolder.appComponent.inject(tapeVM)
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(
            R.id.main_container,
            TabContainerFragment.newInstance("TAPE"),
            "TAPE"
        )
        transaction.commitNow()
        binding.navigationMain.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.tape -> { selectTab("TAPE")
                    true
                }
                R.id.profile -> { selectTab("PROFILE")
                    true
                }
                else -> false
            }
        }
    }

    private fun selectTab(tab : String) {
        val fm = supportFragmentManager
        var currentFragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                currentFragment = f
                break
            }
        }
        val newFragment = fm.findFragmentByTag(tab)
        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return
        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(
                R.id.main_container,
                Tab(tab).createFragment(fm.fragmentFactory), tab
            )
        }
        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }
        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
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

    /**
     *  From https://github.com/terrakok/Cicerone/blob/master/sample/src/main/java/com/github/terrakok/cicerone/sample/ui/bottom/BottomNavigationActivity.kt
     */
    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        for (f in fragments) {
            if (f.isVisible) {
                fragment = f
                break
            }
        }
        Log.d(TAG, "fragment : ${fragment}")
        Log.d(TAG, "onBackPressed")
        Log.d(TAG, "fragment is null : ${fragment == null}")
        Log.d(TAG, "fragment is BackButtonListener ${fragment is BackButtonListener}")
        if (fragment != null && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            Log.d(TAG, "fragment not null")
            return
        } else {
            Log.d(TAG, "standart behavior")
            super.onBackPressed()
        }
    }
}