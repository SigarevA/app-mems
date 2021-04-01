package ru.samsung.itshool.memandos.ui.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.databinding.FragmentDetailMemBinding
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.common.RouterProvider
import java.util.*
import javax.inject.Inject

private const val ARG_MEM = "mem"

class DetailMemFragment : Fragment() {

    private val binding by viewBinding(FragmentDetailMemBinding::bind)

    private val router: Router
        get() = (requireParentFragment() as RouterProvider).router

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ComponentHolder.appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_mem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val mem = it.getParcelable<Mem>(ARG_MEM)!!
            binding.titleMemTextView.text = mem.title
            binding.memDescription.text = mem.description
            binding.dateCreatedMemTV.text = mem.createdDate?.let {
                "${it.getDays()} дней назад"
            }
            mem.photoUrl?.let { imagePath ->
                Glide.with(requireActivity())
                    .load(imagePath)
                    .into(binding.imgMem)
            }
        }
        binding.detailToolbar.setNavigationOnClickListener {
            router.exit()
        }
    }

    companion object {
        @JvmStatic
        fun getInstance(mem: Mem) =
            DetailMemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MEM, mem)
                }
            }
    }
}

fun Long.getDays() = (Date().time - this * 1000) / (1000 * 60 * 60 * 24)