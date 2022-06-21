package com.messenger.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
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


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val chatRoomService = RetrofitInstance.retrofit.create(ChatRoomService::class.java)
    var disposable: Disposable? = null

    private lateinit var chatRoomList: MutableList<ChatRoom>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getChatRoomList(PreferenceApplication.prefs.user)

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
        super.onDestroy()
    }

    private fun getChatRoomList(fromUser: String) {

        val map = mapOf(
            "from_user" to fromUser
        )

        Log.d("chatroom", "!!")

        disposable = chatRoomService.getAll(map)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                chatRoomList = it

                chatRoomList.forEach{ i -> Log.d("chatroom", "$i")}
            }, {
                chatRoomList = mutableListOf()
                Log.e("error_custom", it.message ?: "")
            }, {
                // adapter 설정
                val layoutManager = LinearLayoutManager(this@MainActivity)
                binding.chatRoomView.layoutManager = layoutManager

                binding.chatRoomView.adapter = ChatRoomAdapter(chatRoomList)
                binding.chatRoomView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {

                    }

                    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                        TODO("Not yet implemented")
                    }

                })
            })


    }
}