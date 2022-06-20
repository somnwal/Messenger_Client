package com.messenger.main.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("password")
    val password: String,

    @SerializedName("name")
    val name: String
)