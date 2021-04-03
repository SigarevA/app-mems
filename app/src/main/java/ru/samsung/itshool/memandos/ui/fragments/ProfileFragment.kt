package ru.samsung.itshool.memandos.ui.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Router
import ru.samsung.itshool.memandos.NAME
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.USER_DESCRIPTION
import ru.samsung.itshool.memandos.databinding.FragmentProfileBinding
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.Screens
import ru.samsung.itshool.memandos.ui.VM.ProfileVM
import ru.samsung.itshool.memandos.ui.adapters.MemsAdapter
import ru.samsung.itshool.memandos.ui.common.RouterProvider
import ru.samsung.itshool.memandos.ui.factories.ProfileVmFactory
import ru.samsung.itshool.memandos.utils.SharedPreferencesUtil
import javax.inject.Inject

private const val TAG = "ProfileFragment"
private const val photoURL = "https://i.ibb.co/w06Zg8H/s1200-1.jpg"

class ProfileFragment : Fragment(), MemsAdapter.AdapterInteractionListener {

    private lateinit var profileVM: ProfileVM
    private val adapter = MemsAdapter(this)

    private val binding by viewBinding(FragmentProfileBinding::bind)

    @Inject lateinit var profileVmFactory: ProfileVmFactory

    @Inject lateinit var sharedPreferencesUtil: SharedPreferencesUtil

    private val router : Router
        get() = (requireParentFragment() as RouterProvider).router

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ComponentHolder.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        profileVM = ViewModelProvider(this, profileVmFactory).get(ProfileVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
        fillView()
    }

    private fun initListener() {
        initToolBar()
        profileVM.getMyMemes().observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
            Log.d(TAG, "Size : ${it.size}")
        })
    }

    private fun initToolBar() {
        with((activity as AppCompatActivity)) {
            this.setSupportActionBar(binding.profileToolbar)
        }
    }

    private fun fillView() {
        binding.recyclerViewMyMems.layoutManager =
            StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.recyclerViewMyMems.adapter = adapter
        binding.imgUser.setImgPath(photoURL)

        with(sharedPreferencesUtil) {
            binding.nameUser.text = retriveData(NAME) ?: "name"
            binding.descriptoinUser.text = retriveData(USER_DESCRIPTION) ?: "des"
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
        router.navigateTo(Screens.detailMem(mem))
    }

    override fun onItemShare(mem: Mem) {
        TODO("Not yet implemented")
    }
}
