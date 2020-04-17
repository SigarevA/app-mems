package ru.samsung.itshool.memandos.ui.Activites

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import com.google.android.material.snackbar.Snackbar
import ru.samsung.itshool.memandos.*
import ru.samsung.itshool.memandos.domain.AuthData
import ru.samsung.itshool.memandos.model.response.AuthResponse
import ru.samsung.itshool.memandos.ui.VM.LoginVM

val QUANTITY : Int = 5

class LoginActivity : AppCompatActivity() {

    private val TAG : String = LoginActivity::class.java.name

    private lateinit var loginVM: LoginVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide()
        loginVM = LoginVM()

        Log.d(TAG, "Create activity Login")

        init()
    }


    private fun init() {

        findViewById<TextFieldBoxes>(R.id.text_field_boxes2)
            .setHelperText(String.format("Пароль должен содержать %d символов",
                QUANTITY
            ))

        findViewById<TextFieldBoxes>(R.id.text_field_boxes2).maxCharacters =
            QUANTITY

        val btnLogin = findViewById<Button>(R.id.Loginbtn)
        btnLogin.setOnClickListener {
            val passwordEditText = findViewById<EditText>(R.id.extended_edit_text2)
            if (passwordEditText.text.toString().trim().equals(""))
                findViewById<TextFieldBoxes>(R.id.text_field_boxes2).setError("Поле не может быть пустым", false)
            val loginEditText = findViewById<EditText>(R.id.extended_edit_text)
            if (loginEditText.text.toString().trim().equals(""))
                findViewById<TextFieldBoxes>(R.id.text_field_boxes).setError("Поле не может быть пустым!", true)

            Log.d(TAG, "Listener button")

            loginVM.autheraziton(loginEditText.text.toString(),passwordEditText.text.toString() )
                .observe(this, Observer {
                    it.subscribe(
                        {
                            Log.d(TAG, "Succesful next")
                            this@LoginActivity.showAuthorization(it)
                        },
                        {
                            this@LoginActivity.showError(
                                this@LoginActivity.findViewById(R.id.login_container)
                            )
                        }
                    )
                }
            )

        }
    }


    private fun showError(view : View) {
        val mSnackbar = Snackbar.make(view , "Вы ввели неверные данные.\nПопробуйте еще раз.", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
        val snackbarView : View = mSnackbar.getView()
        snackbarView.setBackgroundResource(R.color.backSnackBar)
        val snackTextView = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        snackTextView.setTextColor(Color.RED)
    }

    @SuppressLint("CommitPrefEdits")
    private fun showAuthorization(responseResult: AuthData? ) {
        val mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)

        val editor = mSettings.edit()
        responseResult?.let {
            editor.putString(APP_ACCESS_TOKEN, responseResult.accessToken)
            editor.putInt(ID, responseResult.userInfo.id)
            editor.putString(NAME, responseResult.userInfo.username)
            editor.putString(FIRST_NAME, responseResult.userInfo.lastName)
            editor.putString(LAST_NAME, responseResult.userInfo.lastName)
            editor.putString(USER_DESCRIPTION, responseResult.userInfo.userDescription)
            editor.apply()
            intent = Intent(this, TapeActivity::class.java)
            startActivity(intent)
            Log.d(TAG, responseResult?.accessToken)
        }
    }
}
