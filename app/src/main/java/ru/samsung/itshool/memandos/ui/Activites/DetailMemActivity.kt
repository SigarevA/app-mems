package ru.samsung.itshool.memandos.ui.Activites

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import org.w3c.dom.Text
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.domain.Mem
import java.time.LocalDate
import java.util.*

private const val TAG = "DetailMemActivity"

private const val MEM_ID = "memID"
private const val MEM_TITLE = "mem_title"
private const val MEM_DESCRIPTION = "MEM_DESCRIPTION"
private const val MEM_FAVORITE = "MEM_FAVORITE"
private const val MEM_DATE_CREATED = "createdDate"
private const val MEM_PHOTO_URL = "photoUrl"

class DetailMemActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var imageMem: ImageView
    private lateinit var toolbar: Toolbar
    private lateinit var memDescription: TextView
    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mem)

        initView()

        fillViews()
    }

    private fun initView() {
        toolbar = findViewById(R.id.main_toolbar)
        title = findViewById(R.id.detail_title_mem)
        imageMem = findViewById(R.id.detail_img_mem)
        memDescription = findViewById(R.id.mem_description)
        dateTextView = findViewById(R.id.date_created_mem)
    }

    private fun fillViews() {
        val intent = getIntent()
        setSupportActionBar(toolbar)

        title.text = intent.getStringExtra(MEM_TITLE)
        memDescription.text = intent.getStringExtra(MEM_DESCRIPTION)

        val photUrl = intent.getStringExtra(MEM_PHOTO_URL)

        imageMem = findViewById(R.id.detail_img_mem)
        Glide.with(this)
            .load(photUrl)
            .into(imageMem)

        dateTextView.text = "${calculateDate()} дней назад"
    }

    private fun calculateDate(): Long {
        val date = intent.getLongExtra(MEM_DATE_CREATED, 0) * 1000
        Log.d(TAG, "date : $date")
        val now = Date()

        val day = (now.time - date) / (1000 * 60 * 60 * 24)

        return day
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
