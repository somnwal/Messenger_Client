package com.messenger.main.service

import com.messenger.main.entity.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {
    @Headers("accept: application/json", "content-type: application/json")
    @POST("/api/users")
    fun register(@Body body: Map<String, String>): Observable<Unit>

    @Headers("accept: application/json", "content-type: application/json")
    @POST("/api/users/token")
    fun updateToken(@Body body: Map<String, String>): Observable<Unit>
}