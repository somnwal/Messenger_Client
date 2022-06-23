package com.messenger.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.messenger.main.adapter.ChatRoomAdapter
import com.messenger.main.databinding.ActivityLoginBinding
import com.messenger.main.databinding.ActivityMainBinding
import com.messenger.main.entity.ChatRoom
import com.messenger.main.pref.PreferenceApplication
import com.messenger.main.retrofit.RetrofitInstance
import com.messenger.main.service.ChatRoomService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val chatRoomService = RetrofitInstance.retrofit.create(ChatRoomService::class.java)
    var disposable: Disposable? = null

    private var chatRoomList = mutableListOf<ChatRoom>()

    private lateinit var chatRoomCoroutine: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.editTextInfo.text = "${PreferenceApplication.prefs.user} 님 환영합니다."

        val layoutManager = LinearLayoutManager(this@MainActivity)
        binding.chatRoomView.layoutManager = layoutManager
        binding.chatRoomView.adapter = ChatRoomAdapter(chatRoomList, this@MainActivity)

        getChatRoomList(PreferenceApplication.prefs.user)

        binding.buttonNewChat.setOnClickListener {
            var i = Intent(this@MainActivity, MessageActivtiy::class.java)
            startActivity(i)
        }

        chatRoomCoroutine = CoroutineScope(Dispatchers.Default + Job()).launch {
            while (true) {
                Log.d("chatroom",chatRoomList.toString())
                getChatRoomList(PreferenceApplication.prefs.user)
                delay(500)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                PreferenceApplication.prefs.remove("user")

                val i = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onDestroy() {
        disposable?.let { disposable!!.dispose() }
        chatRoomCoroutine.cancel()

        super.onDestroy()
    }

    private fun getChatRoomList(fromUser: String) {
        val map = mapOf(
            "from_user" to fromUser
        )

        disposable = chatRoomService.getAll(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.isNotEmpty() && chatRoomList != it) {
                    updateChatRoom(it)
                }
            }, {
                Log.e("error_custom", it.message ?: "")
            }, {
            })
    }

    private fun updateChatRoom(list: MutableList<ChatRoom>) {
        if (chatRoomList.isEmpty()) {
            chatRoomList = list
            binding.chatRoomView.adapter = ChatRoomAdapter(chatRoomList, this@MainActivity)
        } else {
            val size = chatRoomList.size
            chatRoomList = list
            binding.chatRoomView.adapter?.notifyDataSetChanged()
        }


    }
}