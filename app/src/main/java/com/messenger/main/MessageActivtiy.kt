package com.messenger.main

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.messenger.main.adapter.ChatRoomAdapter
import com.messenger.main.adapter.MessageAdapter
import com.messenger.main.databinding.ActivityMessageBinding
import com.messenger.main.entity.ChatRoom
import com.messenger.main.entity.Message
import com.messenger.main.pref.PreferenceApplication
import com.messenger.main.retrofit.RetrofitInstance
import com.messenger.main.service.MessageService
import com.messenger.main.service.UserService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MessageActivtiy : AppCompatActivity() {

    private var disposable: Disposable? = null
    private lateinit var binding: ActivityMessageBinding
    private var userService = RetrofitInstance.retrofit.create(UserService::class.java)
    private var messageService = RetrofitInstance.retrofit.create(MessageService::class.java)

    private lateinit var toUser: String
    private lateinit var toUserName: String

    private lateinit var messageList: MutableList<Message>
    private var lastDate = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toUser = intent.getStringExtra("to_user").toString()
        toUserName = intent.getStringExtra("to_user_name").toString()

        getMessages(lastDate)

        binding.buttonSend.setOnClickListener {
            sendMessage()
            binding.editTextMessage.text.clear()
        }


    }

    private fun getMessages(date: String) {

        var map: Map<String, String> = if(date.isEmpty()) {
            mapOf(
                "from_user" to PreferenceApplication.prefs.user,
                "to_user" to toUser
            )
        } else {
            mapOf(
                "from_user" to PreferenceApplication.prefs.user,
                "to_user" to toUser,
                "date" to date
            )
        }

        disposable = messageService.getAll(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                messageList = it
            }, {
                messageList = mutableListOf()
                Log.e("error", "${it.message}")
            }, {
                // adapter 설정
                val layoutManager = LinearLayoutManager(this@MessageActivtiy)
                binding.messageView.layoutManager = layoutManager
                binding.messageView.adapter = MessageAdapter(messageList, this@MessageActivtiy, toUserName)
                binding.messageView.scrollToPosition(messageList.size - 1)

                lastDate = messageList.last().date
            })
    }

    private fun sendMessage() {
        val map = mapOf(
            "from_user" to PreferenceApplication.prefs.user,
            "to_user" to toUser,
            "msg" to binding.editTextMessage.text.toString()
        )

        disposable = messageService.sendMessage(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
            }, {
                Log.e("error", "${it.message}")
            }, {

            })
    }

    private fun addToMessageList(newList: MutableList<Message>) {
        newList.forEach {
            messageList.add(it)
        }

        lastDate = messageList.last().date
    }
}