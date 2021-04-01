package ru.samsung.itshool.memandos.ui.Activites

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.samsung.itshool.memandos.R
import ru.samsung.itshool.memandos.databinding.ActivityLoginBinding
import ru.samsung.itshool.memandos.di.ComponentHolder
import ru.samsung.itshool.memandos.ui.VM.LoginVM
import ru.samsung.itshool.memandos.utils.SnackBarsUtil
import by.kirich1409.viewbindingdelegate.viewBinding

private val QUANTITY: Int = 5

class LoginActivity : AppCompatActivity() {

    private val TAG: String = LoginActivity::class.java.name

    private lateinit var loginVM: LoginVM
    private val binding by viewBinding(ActivityLoginBinding::bind, R.id.login_container)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_login)
        loginVM = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
            .create(LoginVM::class.java)
        ComponentHolder.appComponent.inject(loginVM)
        initView()
        initListener()
    }


    private fun initView() {
        with(binding.textBoxesPassword) {
            helperText = String.format("Пароль должен содержать %d символов", QUANTITY)
            maxCharacters = QUANTITY
        }
        binding.loginValue.addTextChangedListener(PhoneNumberFormattingTextWatcher("RU"))
    }

    private fun initListener() {
        binding.loginBtn.setOnClickListener {
            val emptyPassword = binding.passwordValue.text.toString().trim().equals("")
            val emptyLogin = binding.loginValue.text.toString().trim().equals("")
            if (emptyPassword)
                binding.textBoxesPassword.setError(
                    getString(R.string.null_text_boxes),
                    false
                )
            if (emptyLogin)
                binding.textBoxesLogin.setError(
                    getString(R.string.null_text_boxes),
                    true
                )
            if (!emptyLogin && !emptyPassword) {
                loginVM.autheraziton(
                    binding.loginValue.text.toString(),
                    binding.passwordValue.text.toString()
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