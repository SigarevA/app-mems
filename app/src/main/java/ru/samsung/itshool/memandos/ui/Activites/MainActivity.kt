package ru.samsung.itshool.memandos.ui.Activites

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.samsung.itshool.memandos.R
import java.util.*
import kotlin.concurrent.schedule

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer = Timer()
        val intent = Intent(this, LoginActivity::class.java)
        timer.schedule(800L) { startActivity(intent) }
    }
}
