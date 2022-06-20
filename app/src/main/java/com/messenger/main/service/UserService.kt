package com.messenger.main.service

import com.messenger.main.dto.UserDto
import com.messenger.main.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET

interface UserService {

    @GET("auth")
    fun getUser(@Body user: UserDto) : Call<UserDto>
}