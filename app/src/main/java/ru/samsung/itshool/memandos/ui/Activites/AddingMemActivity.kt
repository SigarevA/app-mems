package ru.samsung.itshool.memandos.ui.Activites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.databinding.ActivityAddingMemBinding
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.Fragments.DialogFragment
import ru.samsung.itshool.memandos.ui.VM.AddingMemVM
import java.util.*

private const val TAG = "AddingMemActivity"
private const val SUCCESS_ADDING = "SUCCESS"
private const val photoURL = "https://i.ibb.co/Tt9N3Xc/prikoli-15.jpg"

class AddingMemActivity : AppCompatActivity() {

    private var createBtn: MenuItem? = null
    private val binding by viewBinding(ActivityAddingMemBinding::bind, R.id.root)

    private lateinit var addingMemVM: AddingMemVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_mem)

        addingMemVM = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(AddingMemVM::class.java)
        ComponentHolder.appComponent.inject(addingMemVM)
        initListener()

        Glide.with(this)
            .load(photoURL)
            .into(binding.representationMem)
    }

    private fun initListener() {
        binding.headMemText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                createBtn?.let {
                    if (p0.toString().trim().isNotEmpty() && !it.isEnabled)
                        it.isEnabled = true
                    if (p0.toString().trim().isEmpty() && it.isEnabled)
                        it.isEnabled = false
                }
            }
        })
        binding.btnAddingImgMem.setOnClickListener(this::handleClickBtn)
        setSupportActionBar(binding.toolbarAddingMem)
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adding_mem_menu, menu)
        createBtn = menu.findItem(R.id.create_mem_btn)
        createBtn?.isEnabled = false
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.create_mem_btn -> {
                addingMemVM.saveMem(
                    Mem(
                        title = binding.headMemText.text.toString(),
                        description = binding.descriptionMemText.text.toString(),
                        createdDate = Date().time,
                        photoUrl = photoURL
                    )
                )
                    .observe(this, androidx.lifecycle.Observer {
                        val data = Intent()
                        when {
                            it.isSuccess -> data.putExtra(SUCCESS_ADDING, true)
                            it.isFailure -> data.putExtra(SUCCESS_ADDING, false)
                        }
                        setResult(Activity.RESULT_OK, data)
                        finish()
                    })
                return true
            }
        }
        return false
    }

    fun handleClickBtn(v: View) {
        val dialog = DialogFragment()
        val ft = supportFragmentManager.beginTransaction()
    }

    companion object {
        fun getResult(data: Intent): Boolean = data.getBooleanExtra(SUCCESS_ADDING, false)
    }
}