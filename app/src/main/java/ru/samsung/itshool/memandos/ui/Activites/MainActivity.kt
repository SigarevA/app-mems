package ru.samsung.itshool.memandos.ui.Activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    val TAG: String = MainActivity::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val timer: Timer = Timer()
        val intent: Intent = Intent(this, LoginActivity::class.java)
        timer.schedule(800L) { startActivity(intent) }
    }
}
