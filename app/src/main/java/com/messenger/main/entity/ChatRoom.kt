package com.messenger.main.entity

import com.google.gson.annotations.SerializedName

data class ChatRoom(
    @SerializedName("from_user")
    val fromUser: String,

    @SerializedName("to_user")
    val toUser: String,

    @SerializedName("from_user_name")
    val fromUserName: String,

    @SerializedName("to_user_name")
    val toUserName: String,

    @SerializedName("date")
    val date: String,

    @SerializedName("last_message")
    val lastMessage: String
)