package ru.samsung.itshool.memandos.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.subnavigation.LocalCiceroneHolder
import ru.samsung.itshool.memandos.ui.Screens
import ru.samsung.itshool.memandos.ui.common.BackButtonListener
import ru.samsung.itshool.memandos.ui.common.RouterProvider
import java.lang.IllegalArgumentException
import javax.inject.Inject

private const val ARG_TAB_NAME = "tabName"
private const val TAG = "TabContainerFragment"

class TabContainerFragment : Fragment(), BackButtonListener, RouterProvider {
    private var param1: String? = null

    private val navigator by lazy { AppNavigator( requireActivity(), R.id.tab_container, childFragmentManager ) }

    @Inject
    lateinit var localRouter: LocalCiceroneHolder

    private val containerName : String
            get() = requireArguments().getString(ARG_TAB_NAME)!!

    private val cicerone: Cicerone<Router>
        get() = localRouter.getCicerone(containerName)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ComponentHolder.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_TAB_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tab_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (childFragmentManager.findFragmentById(R.id.tab_container) == null) {
            when (containerName) {
                "TAPE" -> router.replaceScreen(Screens.ribbon())
                "PROFILE" -> router.replaceScreen(Screens.Profile())
                "ADDING_MEM" -> router.replaceScreen(Screens.AddingMem())
                else -> throw IllegalArgumentException("Container name not recognized!")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator = navigator)
    }

    override fun onPause() {
        cicerone.getNavigatorHolder().removeNavigator()
        super.onPause()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            TabContainerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TAB_NAME, param1)
                }
            }
    }

    override fun onBackPressed(): Boolean {
        val fragment = childFragmentManager.findFragmentById(R.id.tab_container)
        if (fragment != null ) {
            router.exit()
        }
        return true
    }

    override val router: Router
        get() = cicerone.router
}