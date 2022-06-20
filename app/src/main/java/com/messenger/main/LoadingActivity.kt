package com.messenger.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.messenger.main.pref.PreferenceApplication

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val initThread: Thread = object : Thread() {
            override fun run() {
                try {
                    super.run()

                    val user = PreferenceApplication.prefs.user

                    user?.let {
                        val i = Intent(
                            this@LoadingActivity,
                            MainActivity::class.java
                        )
                        startActivity(i)
                        finish()
                    }

                    val i = Intent(
                        this@LoadingActivity,
                        LoginActivity::class.java
                    )
                    startActivity(i)
                    finish()

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        initThread.start()
    }
}