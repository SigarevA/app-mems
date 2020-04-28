package ru.samsung.itshool.memandos.ui.Fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
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
import ru.samsung.itshool.memandos.ui.InfoUserView

private const val TAG = "ProfileFragment"
private const val photoURL = "https://i.ibb.co/w06Zg8H/s1200-1.jpg"

class ProfileFragment : Fragment(), MemsAdapter.AdapterInteractionListener {

    private lateinit var nameUserTextView: TextView
    private lateinit var descriptionUserTextView: TextView
    private lateinit var profileToolbar: Toolbar
    private lateinit var memesRecyrcleView: RecyclerView
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private lateinit var imgUser: InfoUserView


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
        val v = inflater.inflate(R.layout.fragment_profile, container, false)

        initView(v)
        initListener(v)

        fillView()

        return v
    }

    private fun initListener(v: View) {
        initToolBar(v)

        context?.let {
            profileVM.getMyMemes(it).observe(viewLifecycleOwner, Observer {
                val memsAdapter2 = MemsAdapter(it.toTypedArray(), this)
                memesRecyrcleView.adapter = memsAdapter2
                Log.d(TAG, "Size : ${it.size}")
            })
        }
    }

    private fun initToolBar(v: View) {
        profileToolbar = v.findViewById(R.id.profile_toolbar)
        profileToolbar.title = ""

        with((activity as AppCompatActivity)) {
            this.setSupportActionBar(profileToolbar)
        }
    }

    private fun initView(v: View) {
        nameUserTextView = v.findViewById(R.id.name_user)
        descriptionUserTextView = v.findViewById(R.id.descriptoin_user)
        memesRecyrcleView = v.findViewById(R.id.recycler_view_my_mems)
        imgUser = v.findViewById(R.id.img_user)
    }

    private fun fillView() {
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        memesRecyrcleView.layoutManager = staggeredGridLayoutManager
        imgUser.setImgPath(photoURL)

        context?.let {
            with(SharedPreferencesUtli) {
                nameUserTextView.text = retriveData(it, NAME) ?: "name"
                descriptionUserTextView.text = retriveData(it, USER_DESCRIPTION) ?: "des"
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        Log.d(TAG, "options menu")
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.user_logout -> {
                activity?.let {
                    val dialog = LogoutDialogFragment()
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
}
