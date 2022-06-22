package com.messenger.main.entity

import com.google.gson.annotations.SerializedName

data class Message (
    @SerializedName("from_user")
    val fromUser: String,

    @SerializedName("to_user")
    val toUser: String,

    @SerializedName("msg")
    val msg: String,

    @SerializedName("date")
    val date: String
)