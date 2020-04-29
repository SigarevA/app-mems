package ru.samsung.itshool.memandos.ui.Activites

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_login.*
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes
import ru.samsung.itshool.memandos.*
import ru.samsung.itshool.memandos.ui.VM.LoginVM
import ru.samsung.itshool.memandos.utils.SnackBarsUtil

private val QUANTITY: Int = 5

class LoginActivity : AppCompatActivity() {

    private val TAG: String = LoginActivity::class.java.name

    private lateinit var loginVM: LoginVM

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


    private fun initView() {
        with(textBoxesPassword) {
                setHelperText(String.format("Пароль должен содержать %d символов", QUANTITY))
                maxCharacters = QUANTITY
        }
    }

    private fun initListener() {

        loginBtn.setOnClickListener {
            val emptyPassword = passwordValue.text.toString().trim().equals("")
            val emptyLogin = loginValue.text.toString().trim().equals("")
            if (emptyPassword)
                textBoxesPassword.setError(
                    getString(R.string.null_text_boxes),
                    false
                )
            if (emptyLogin)
                textBoxesLogin.setError(
                    getString(R.string.null_text_boxes),
                    true
                )
            if (!emptyLogin && !emptyPassword) {
                loginVM.autheraziton(
                    loginValue.text.toString(),
                    passwordValue.text.toString()
                )
                    .observe(this, Observer {
                        when {
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

    private fun errorAuthorize(view: View) {
        SnackBarsUtil.errorSnackBar(getString(R.string.invalid_auth), view)
    }

    @SuppressLint("CommitPrefEdits")
    private fun successAuthorize() {
        intent = Intent(this, TapeActivity::class.java)
        startActivity(intent)
    }
}

