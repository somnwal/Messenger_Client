package com.messenger.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.messenger.main.databinding.ActivityMessageBinding
import io.reactivex.disposables.Disposable

class MessageActivtiy : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var binding: ActivityMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}