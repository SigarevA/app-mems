package ru.samsung.itshool.memandos.ui.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.domain.Mem
import ru.samsung.itshool.memandos.model.repo.MemDatabase
import ru.samsung.itshool.memandos.ui.Fragments.DialogFragment
import ru.samsung.itshool.memandos.utils.SnackBarsUtil
import java.util.*

class AddingMemActivity : AppCompatActivity() {


    private lateinit var toolbar: Toolbar
    private lateinit var titleMemTextView : EditText
    private lateinit var textMemTextView : EditText
    private lateinit var imgMemImageView : ImageView
    private lateinit var imgMemBtn : ImageButton
    private lateinit var createBtn : MenuItem


    private lateinit var memDataBase : MemDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_mem)

        memDataBase = MemDatabase.getInstance(this)

        init()

        Glide.with(this)
            .load(photoURL)
            .into(imgMemImageView)
    }


    private fun init() {
        toolbar = findViewById(R.id.toolbar_adding_mem)
        titleMemTextView = findViewById(R.id.head_mem_text)
        textMemTextView = findViewById(R.id.description_mem_text)
        imgMemImageView = findViewById(R.id.representation_mem)
        imgMemBtn = findViewById(R.id.btn_adding_img_mem)



        titleMemTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().length != 0 && !createBtn.isEnabled)
                    createBtn.isEnabled = true
                if (p0.toString().trim().length == 0 && createBtn.isEnabled)
                    createBtn.isEnabled = false

                Log.d(TAG, String.format("changeText , p1 : %d, p2 : %d, p3 : %d ; $p0", p1, p2, p3))
            }
        })


        imgMemBtn.setOnClickListener(this::handleClickBtn)

        setSupportActionBar(toolbar)
    }


    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        menuInflater.inflate(R.menu.adding_mem_menu, menu)

        createBtn = menu.findItem (R.id.create_mem_btn)
        createBtn.isEnabled = false

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.create_mem_btn -> {
                Log.d(TAG, "Click button in menu")
                memDataBase.memDao().insert(
                    Mem(title = titleMemTextView.text.toString(),
                        description = textMemTextView.text.toString(),
                        createdDate = Date().time,
                        photoUrl = photoURL
                        )
                )
                    .subscribeOn(Schedulers.io())
                    .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
                    .subscribe (
                        {
                            Log.d(TAG, "success")
                            finish()
                        },
                        {}
                    )

                return true
            }

        }
        return false
    }


    fun handleClickBtn(v : View) {
        Log.d(TAG, "Click button")
        val dialog = DialogFragment()
        val ft = supportFragmentManager.beginTransaction()
        dialog.show(ft, "dialog")
    }

    companion object {
        const val TAG = "AddingMemActivity"
        const val photoURL = "https://i.ibb.co/7jyvKdP/c3a403eaf82be4ac51ed8c632c3089c5-f24d80acb4ee32776f2667ff8d6452cb2ca88fa8.jpg"
    }

}
