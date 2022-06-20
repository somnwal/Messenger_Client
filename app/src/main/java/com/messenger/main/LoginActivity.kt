package com.messenger.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.messenger.main.databinding.ActivityLoginBinding
import com.messenger.main.dto.UserDto
import com.messenger.main.retrofit.RetrofitInstance
import com.messenger.main.service.AuthService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonLogin.setOnClickListener {
            val authService = RetrofitInstance.retrofit.create(AuthService::class.java)

            val id = binding.editTextID.text.toString()
            val password = binding.editTextPassword.text.toString()

            val map = mapOf("id" to id, "password" to password)
            var call: Call<UserDto> = authService.login(map)

            call.enqueue(object : Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    if(response.code() == 200) {
                        val i = Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                        startActivity(i)
                        finish()
                    } else {
                        AlertDialog
                            .Builder(this@LoginActivity)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("알림")
                            .setMessage("아이디 혹은 비밀번호가 일치하지 않습니다.")
                            .setPositiveButton("확인", null)
                            .show()
                    }
                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    Log.e("error", "$t")
                }

            })
        }
    }
}