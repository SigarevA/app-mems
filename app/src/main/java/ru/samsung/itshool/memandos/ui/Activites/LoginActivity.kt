package ru.samsung.itshool.memandos.ui.Activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import ru.samsung.itshool.memandos.*
import ru.samsung.itshool.memandos.ui.VM.LoginVM
import ru.samsung.itshool.memandos.utils.SnackBarsUtil

val QUANTITY : Int = 5

class LoginActivity : AppCompatActivity() {

    private val TAG : String = LoginActivity::class.java.name

    private lateinit var loginVM: LoginVM
    private lateinit var btnLogin : AppCompatButton
    private lateinit var passwordEditText : EditText
    private lateinit var loginEditText : EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        getSupportActionBar()?.hide()
        loginVM = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(LoginVM::class.java)

        Log.d(TAG, "Create activity Login")


        initView()

        initListener()
    }


    private fun initView(){
        findViewById<TextFieldBoxes>(R.id.text_field_boxes_password)
            .setHelperText(String.format("Пароль должен содержать %d символов",
                QUANTITY
            ))
        findViewById<TextFieldBoxes>(R.id.text_field_boxes_login).maxCharacters = QUANTITY
        btnLogin = findViewById(R.id.Loginbtn)
        passwordEditText = findViewById(R.id.login_value)
        loginEditText = findViewById(R.id.password_value)
    }

    private fun initListener() {

        btnLogin.setOnClickListener {
            val emptyPassword = passwordEditText.text.toString().trim().equals("")
            val emptyLogin = loginEditText.text.toString().trim().equals("")
            if (emptyPassword)
                findViewById<TextFieldBoxes>(R.id.text_field_boxes_password).setError(
                    "Поле не может быть пустым",
                    false
                )
            if (emptyLogin)
                findViewById<TextFieldBoxes>(R.id.text_field_boxes_login).setError(
                    "Поле не может быть пустым!",
                    true
                )
            if (!emptyLogin && !emptyPassword) {
                loginVM.autheraziton(loginEditText.text.toString(),passwordEditText.text.toString() )
                    .observe(this, Observer {
                        when{
                            it.isSuccess -> {
                                successAuthorize()
                            }
                            it.isFailure -> {
                                errorAuthorize(this@LoginActivity.findViewById(R.id.login_container))
                            }
                        }
                    }
                )
            }
        }
    }




    private fun errorAuthorize(view : View) {
        SnackBarsUtil.errorSnackBar("Вы ввели неверные данные.\nПопробуйте еще раз.", view)
    }

    @SuppressLint("CommitPrefEdits")
    private fun successAuthorize() {
        intent = Intent(this, TapeActivity::class.java)
        startActivity(intent)
    }
}

