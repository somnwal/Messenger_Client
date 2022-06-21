package com.messenger.main.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.messenger.main.MainActivity
import com.messenger.main.MessageActivtiy
import com.messenger.main.R
import com.messenger.main.databinding.RecyclerChatroomBinding
import com.messenger.main.entity.ChatRoom

class ChatRoomViewHolder(val binding: RecyclerChatroomBinding) : RecyclerView.ViewHolder(binding.root)

// Adapter 구성
class ChatRoomAdapter(val datas: MutableList<ChatRoom>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // 각 항목의 뷰 홀더를 준비해서 리턴, 자동으로 재사용.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ChatRoomViewHolder(
            RecyclerChatroomBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    // 각각의 항목 구성
    // 첫번째 매개변수가 onCreateViewHolder 에서 리턴 시킨 객체다.
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as ChatRoomViewHolder).binding
        binding.textName.text = "${datas[position].toUserName} 님과의 대화"
        binding.textContent.text = datas[position].lastMessage
        binding.textDate.text = datas[position].date.replace("T", " ")

    }

    override fun getItemCount(): Int {
        return datas.size
    }



}