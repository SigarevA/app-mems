package ru.samsung.itshool.memandos.ui.Fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import ru.samsung.itshool.memandos.NAME
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.USER_DESCRIPTION
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.Activites.DetailMemActivity
import ru.samsung.itshool.memandos.ui.Activites.LoginActivity
import ru.samsung.itshool.memandos.ui.VM.ProfileVM
import ru.samsung.itshool.memandos.ui.adapters.MemsAdapter
import ru.samsung.itshool.memandos.utils.SharedPreferencesUtli
import ru.samsung.itshool.memandos.utils.SnackBarsUtil


class ProfileFragment : Fragment(), MemsAdapter.AdapterInteractionListener,
    LogoutDialogFragment.NoticeDialogListener {

    private lateinit var nameUserTextView : TextView
    private lateinit var descriptionUserTextView : TextView
    private lateinit var profileToolbar : Toolbar
    private lateinit var memesRecyrcleView : RecyclerView
    private lateinit var staggeredGridLayoutManager : StaggeredGridLayoutManager

    private lateinit var profileVM: ProfileVM


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        profileVM = ViewModelProvider(this).get(ProfileVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_profile, container, false)
        init(v)

        return v
    }


    private fun init(v : View) {
        initToolBar(v)

        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        nameUserTextView = v.findViewById(R.id.name_user)
        descriptionUserTextView = v.findViewById(R.id.descriptoin_user)
        memesRecyrcleView = v.findViewById(R.id.recycler_view_my_mems)

        memesRecyrcleView.layoutManager = staggeredGridLayoutManager
        val img = v.findViewById<ImageView>(R.id.img_user)


        context?.let{
            with(SharedPreferencesUtli) {
                Glide
                    .with(v)
                    .load(photoURL )
                    .into(img)
                nameUserTextView.text = retriveData(it, NAME) ?: "name"
                descriptionUserTextView.text = retriveData(it, USER_DESCRIPTION) ?: "des"
            }
        }


        context?.let{
            profileVM.getMyMemes(it).observe(viewLifecycleOwner, Observer {
                val memsAdapter2 = MemsAdapter( it.toTypedArray(), this)
                memesRecyrcleView.adapter = memsAdapter2
                Log.d(TAG, "Size : ${it.size}")
            })
        }
    }


    fun initToolBar(v : View) {
        profileToolbar = v.findViewById(R.id.profile_toolbar)
        profileToolbar.title = ""

        with((activity as AppCompatActivity)){
            this.setSupportActionBar(profileToolbar)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        Log.d(TAG, "options menu")
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.user_logout -> {
                activity?.let {
                    val dialog = LogoutDialogFragment(this)
                    val ft = it.supportFragmentManager.beginTransaction()
                    dialog.show(ft, "dialog")
                }
            }
        }


        return false
    }

    override fun onItemClick(mem: Mem) {
        context?.let {
            val intent = DetailMemActivity.getIntent(it, mem)
            startActivity(intent)
        }
    }

    companion object {
        const val TAG = "ProfileFragment"
        const val photoURL = "https://i.ibb.co/7jyvKdP/c3a403eaf82be4ac51ed8c632c3089c5-f24d80acb4ee32776f2667ff8d6452cb2ca88fa8.jpg"
    }

    override fun onDialogPositiveClick() {
        profileVM.logout().observe(this, Observer {
            when{
                it.isSuccess -> {
                    val intent = Intent(activity, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                it.isFailure -> {
                    SnackBarsUtil.errorSnackBar("Выйти не удалось", memesRecyrcleView)
                }
            }
        })
    }
}
