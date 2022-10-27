package com.example.whooby

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Auth_Interface {

    @POST("/login")
    fun executeLogin(@Body map: HashMap<String, String>): Call<LoginResult?>?

    @POST("/signup")
    fun executeSignup(@Body map: HashMap<String, String>): Call<Void?>?
}