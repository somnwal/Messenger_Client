package com.messenger.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val welcomeThread: Thread = object : Thread() {
            override fun run() {
                try {
                    super.run()
                    sleep(10000) //Delay of 10 seconds
                } catch (e: Exception) {
                } finally {
                    val i = Intent(
                        this@LoadingActivity,
                        MainActivity::class.java
                    )
                    startActivity(i)
                    finish()
                }
            }
        }
        welcomeThread.start()
    }
}