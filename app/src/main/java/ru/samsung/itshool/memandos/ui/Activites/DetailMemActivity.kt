package ru.samsung.itshool.memandos.ui.Activites

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail_mem.*
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.domain.Mem
import java.util.*

private const val TAG = "DetailMemActivity"

private const val MEM_ID = "memID"
private const val MEM_TITLE = "mem_title"
private const val MEM_DESCRIPTION = "MEM_DESCRIPTION"
private const val MEM_FAVORITE = "MEM_FAVORITE"
private const val MEM_DATE_CREATED = "createdDate"
private const val MEM_PHOTO_URL = "photoUrl"

class DetailMemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mem)

        initView()
    }

    private fun initView() {
        val intent = intent
        setSupportActionBar(detailToolbar)

        titleMemTextView.text = intent.getStringExtra(MEM_TITLE)
        memDescription.text = intent.getStringExtra(MEM_DESCRIPTION)

        val photUrl = intent.getStringExtra(MEM_PHOTO_URL)

        Glide.with(this)
            .load(photUrl)
            .into(imgMem)

        dateCreatedMemTV.text = Editable.Factory().newEditable("${calculateDate()} дней назад")
    }

    private fun calculateDate(): Long {
        val date = intent.getLongExtra(MEM_DATE_CREATED, 0) * 1000
        Log.d(TAG, "date : $date")
        val now = Date()

        return (now.time - date) / (1000 * 60 * 60 * 24)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_mem_menu, menu)
        return true
    }

    companion object {

        fun getIntent(context: Context, mem: Mem): Intent {

            val intent = Intent(context, DetailMemActivity::class.java)
            intent.putExtra(MEM_ID, mem.id)
            intent.putExtra(MEM_TITLE, mem.title)
            intent.putExtra(MEM_DESCRIPTION, mem.description)
            intent.putExtra(MEM_FAVORITE, mem.isFavorite)
            intent.putExtra(MEM_DATE_CREATED, mem.createdDate)
            intent.putExtra(MEM_PHOTO_URL, mem.photoUrl)

            Log.d(TAG, "date : " + mem.createdDate)

            return intent
        }

    }
}
