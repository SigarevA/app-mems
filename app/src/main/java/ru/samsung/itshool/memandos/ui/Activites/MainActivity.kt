package ru.samsung.itshool.memandos.ui.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.samsung.itshool.memandos.R
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    val TAG : String = MainActivity::class.java.name;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getSupportActionBar()?.hide()

        val timer : Timer = Timer()
        val intent : Intent = Intent(this, LoginActivity::class.java)
        timer.schedule ( 800L) {  startActivity(intent) }
    }
}
