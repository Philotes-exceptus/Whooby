package com.example.whooby

import com.google.gson.annotations.SerializedName

class LoginResult {

    @SerializedName("password")
    private val password : String = ""

    @SerializedName("reg_no")
    private val reg_no : String = ""

    @SerializedName("email")
    private val email : String = ""

    fun getPassword(): String {
        return password
    }

    fun getEmail(): String {
        return email
    }

    fun getReg_no(): String {
        return reg_no
    }

}