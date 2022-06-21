package com.messenger.main

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.messenger.main.databinding.ActivityRegisterBinding
import com.messenger.main.pref.PreferenceApplication
import com.messenger.main.retrofit.RetrofitInstance
import com.messenger.main.service.AuthService
import com.messenger.main.service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RegisterActivity : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var binding: ActivityRegisterBinding
    private var userService = RetrofitInstance.retrofit.create(UserService::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRegister.setOnClickListener {
            val map = mapOf<String, String>(
                "id" to binding.editTextID.text.toString(),
                "password" to binding.editTextPassword.text.toString(),
                "name" to binding.editTextName.text.toString()
            )

            register(map)
        }
    }

    override fun onDestroy() {
        disposable?.let { disposable!!.dispose() }
        super.onDestroy()
    }

    private fun register(map: Map<String, String>) {
        // 로그인 실행
        disposable = userService.register(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                AlertDialog
                    .Builder(this@RegisterActivity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setMessage("회원가입이 완료되었습니다.")
                    .setPositiveButton("확인") { _: DialogInterface, _: Int ->
                        finish()
                    }
                    .show()

            }, {
                AlertDialog
                    .Builder(this@RegisterActivity)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("알림")
                    .setMessage("입력한 값에 오류가 있거나 중복된 아이디 혹은 닉네임이 있습니다.")
                    .setPositiveButton("확인", null)
                    .show()
            }, {

            })
    }

}