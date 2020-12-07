package ru.samsung.itshool.memandos.ui.Activites

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.ui.Fragments.DialogFragment
import ru.samsung.itshool.memandos.ui.VM.AddingMemVM
import java.util.*

private const val TAG = "AddingMemActivity"
private const val SUCCESS_ADDING = "SUCCESS"
private const val photoURL =
    "https://i.ibb.co/Tt9N3Xc/prikoli-15.jpg"

class AddingMemActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var titleMemTextView: EditText
    private lateinit var textMemTextView: EditText
    private lateinit var imgMemImageView: ImageView
    private lateinit var imgMemBtn: ImageButton
    private var createBtn: MenuItem? = null

    private lateinit var addingMemVM: AddingMemVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_mem)

        addingMemVM = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(AddingMemVM::class.java)
        ComponentHolder.appComponent.inject(addingMemVM)

        initView()
        initListener()

        Glide.with(this)
            .load(photoURL)
            .into(imgMemImageView)
    }


    private fun initView() {
        toolbar = findViewById(R.id.toolbar_adding_mem)
        titleMemTextView = findViewById(R.id.head_mem_text)
        textMemTextView = findViewById(R.id.description_mem_text)
        imgMemImageView = findViewById(R.id.representation_mem)
        imgMemBtn = findViewById(R.id.btn_adding_img_mem)
    }


    private fun initListener() {
        titleMemTextView.addTextChangedListener(object : TextWatcher {
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
        Log.d(TAG, "init listener")
        imgMemBtn.setOnClickListener(this::handleClickBtn)
        setSupportActionBar(toolbar)
    }


    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adding_mem_menu, menu)
        Log.d(TAG, "method cteate panel menu")
        createBtn = menu.findItem(R.id.create_mem_btn)
        createBtn?.isEnabled = false
        Log.d(TAG, "init listener")
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "Click button in menu")
        when (item.itemId) {
            R.id.create_mem_btn -> {
                Log.d(TAG, "Click button in menu")
                Log.d(TAG, "DATE : ${Date().time}")
                addingMemVM.saveMem(
                    Mem(
                        title = titleMemTextView.text.toString(),
                        description = textMemTextView.text.toString(),
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
                        Log.d(TAG, "Adding finish!")
                        setResult(Activity.RESULT_OK, data)
                        finish()
                    })
                return true
            }
        }
        return false
    }

    fun handleClickBtn(v: View) {
        Log.d(TAG, "Click button")
        val dialog = DialogFragment()
        val ft = supportFragmentManager.beginTransaction()
        dialog.show(ft, "dialog")
    }

    companion object {
        fun getResult(data: Intent): Boolean = data.getBooleanExtra(SUCCESS_ADDING, false)
    }
}