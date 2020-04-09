package ru.samsung.itshool.memandos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    val TAG : String = MainActivity::class.java.name;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timer : Timer = Timer()
        val intent : Intent = Intent(this, LoginActivity::class.java)
        timer.schedule ( 800L) {  startActivity(intent) }
    }
}
