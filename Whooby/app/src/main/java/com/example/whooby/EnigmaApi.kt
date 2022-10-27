package com.example.whooby

import retrofit2.Response
import retrofit2.http.GET

interface EnigmaApi {

   @GET("/blog")
    suspend fun getBlog() : Response<EnigmaBlogs>
}