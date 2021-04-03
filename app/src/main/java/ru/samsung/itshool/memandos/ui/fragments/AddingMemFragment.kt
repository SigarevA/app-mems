package ru.samsung.itshool.memandos.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.databinding.FragmentAddingMemBinding
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.VM.AddingMemResult
import ru.samsung.itshool.memandos.ui.VM.AddingMemVM
import ru.samsung.itshool.memandos.utils.SnackBarsUtil
import java.util.*

private const val photoURL = "https://i.ibb.co/Tt9N3Xc/prikoli-15.jpg"

class AddingMemFragment : Fragment() {

    private val binding by viewBinding(FragmentAddingMemBinding::bind)

    private lateinit var vm : AddingMemVM

    override fun onAttach(context: Context) {
        super.onAttach(context)
        vm = ViewModelProvider(this).get(AddingMemVM::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adding_mem, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm.eventLiveData.observe(viewLifecycleOwner) {
            when(it) {
                is AddingMemResult.SuccessAddingResult -> {
                    SnackBarsUtil.successSnackBar("Success", binding.root)
                }
            }
        }
        initListeners()
    }

    private fun initListeners() {
        binding.toolbarAddingMem.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.create_mem_btn -> {
                    vm.saveMem(getMemFromBinding())
                    true
                }
                else -> false
            }
        }
    }

    private fun getMemFromBinding(date : Date = Date()) : Mem {
        return Mem(
            title = binding.headMemText.text.toString(),
            description = binding.descriptionMemText.text.toString(),
            createdDate = date.time,
            photoUrl = photoURL
        )
    }
}