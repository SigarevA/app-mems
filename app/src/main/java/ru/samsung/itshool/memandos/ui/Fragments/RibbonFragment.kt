package ru.samsung.itshool.memandos.ui.Fragments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.github.terrakok.cicerone.Navigator
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.Creator
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.databinding.FragmentRibbonBinding
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.Activites.DetailMemActivity
import ru.samsung.itshool.memandos.ui.Screens
import ru.samsung.itshool.memandos.ui.VM.RobbionVM
import ru.samsung.itshool.memandos.ui.adapters.MemsAdapter
import ru.samsung.itshool.memandos.ui.common.BackButtonListener
import ru.samsung.itshool.memandos.ui.common.RouterProvider
import ru.samsung.itshool.memandos.utils.SnackBarsUtil
import javax.inject.Inject

private const val TAG = "RibbonFragment"

class RibbonFragment : Fragment(), MemsAdapter.AdapterInteractionListener,
    SearchView.OnQueryTextListener, BackButtonListener {

    private val binding by viewBinding(FragmentRibbonBinding::bind)
    private lateinit var staggeredGridLayoutManager: StaggeredGridLayoutManager
    private val memsAdapter = MemsAdapter(this)
    private lateinit var robbionVM: RobbionVM

    private val router : Router
        get() = ( requireParentFragment() as RouterProvider).router


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ComponentHolder.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "create ribbon fragment")
        setHasOptionsMenu(true)
        robbionVM = ViewModelProvider(this).get(RobbionVM::class.java)
        ComponentHolder.appComponent.inject(robbionVM)
    }

    fun bindingView() {
        with((activity as AppCompatActivity)) {
            this.setSupportActionBar(binding.mainToolbar)
        }
        binding.swipeContainer.setOnRefreshListener {
            robbionVM.refreshMemes()
        }
        staggeredGridLayoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        binding.recyclerViewMems.adapter = memsAdapter
        binding.recyclerViewMems.layoutManager = staggeredGridLayoutManager
    }

    fun initVM() {
        robbionVM.memLiveData.observe(viewLifecycleOwner, Observer<Collection<Mem>> {
            successLoadMemes(binding.root, it as List<Mem>)
        })
        robbionVM.eventLiveData.observe(viewLifecycleOwner, Observer { event ->
            SnackBarsUtil.successSnackBar(event, binding.root)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ribbon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindingView()
        initVM()
        robbionVM.getMemes()
    }

    fun successLoadMemes(v: View, memes: List<Mem>) {
        memsAdapter.submitList(memes)
        binding.swipeContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    fun failureLoadMemes() {
        binding.progressBar.visibility = View.GONE
        binding.textFailure.visibility = View.VISIBLE
    }

    fun failureLoad(v: View) {
        binding.swipeContainer.isRefreshing = false
        SnackBarsUtil.errorSnackBar(R.string.failureRefresh.toString(), v)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onItemClick(mem: Mem) {
        context?.let {
            router.navigateTo(  FragmentScreen {  DetailMemFragment.getInstance(mem) })
        }
    }

    override fun onItemShare(mem: Mem) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                "${mem.title}\n ${mem.description} \n ${mem.photoUrl}"
            )
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.d(TAG, "text : $query")
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "text $newText")
        return false
    }

    companion object {
        @JvmStatic
        fun newInstance() = RibbonFragment()
    }

    override fun onBackPressed(): Boolean {
        router.navigateTo(Screens.Profile())
        return true
    }
}