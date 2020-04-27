package ru.samsung.itshool.memandos.ui.Activites

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

class DetailMemActivity : AppCompatActivity() {

    private lateinit var title : TextView
    private lateinit var imageMem : ImageView
    private lateinit var toolbar : Toolbar
    private lateinit var memDescription : TextView
    private lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_mem)

        val intent = getIntent()

        toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)

        intent.getStringExtra("name")
        intent.getLongExtra(MEM_ID, 0)

        title = findViewById<TextView>(R.id.detail_title_mem)
        title.text = intent.getStringExtra(MEM_TITLE)

        imageMem = findViewById<ImageView>(R.id.detail_img_mem)

        val photUrl = intent.getStringExtra(MEM_PHOTO_URL)

        imageMem = findViewById(R.id.detail_img_mem)
        Glide.with(this)
            .load(photUrl)
            .into(imageMem)


        memDescription = findViewById(R.id.mem_description)
        memDescription.text = intent.getStringExtra(MEM_DESCRIPTION)

        dateTextView = findViewById(R.id.date_created_mem)

        val date = intent.getLongExtra(MEM_DATE_CREATED, 0) * 1000
        Log.d(TAG, "date : $date")
        val now = Date()

        val day = (now.time - date) / (1000 * 60 * 60 * 24)


        dateTextView.text = "$day дней назад"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_mem_menu, menu)
        return true
    }

    companion object {


        const val TAG = "DetailMemActivity"

        const val MEM_ID = "memID"
        const val MEM_TITLE = "mem_title"
        const val MEM_DESCRIPTION = "MEM_DESCRIPTION"
        const val MEM_FAVORITE = "MEM_FAVORITE"
        const val MEM_DATE_CREATED = "createdDate"
        const val MEM_PHOTO_URL = "photoUrl"


        fun getIntent(context: Context ,mem : Mem) : Intent{

            val intent =  Intent(context, DetailMemActivity::class.java)
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
