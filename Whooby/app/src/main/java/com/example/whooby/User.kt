package com.example.whooby

class User {
    var regd: String?= null
    var name: String? = "golu"
    var inputtext: String? = ""


    @JvmName("setName1")
    fun setName(lastname: String) {
        this.name = name
    }

    @JvmName("setStatement1")
    fun setStatement(lastname: String) {
        this.inputtext = inputtext
    }

    @JvmName("getStatement1")
    fun getStatement(): String? {
        return inputtext
    }

    @JvmName("getName1")
    fun getName(): String? {
        return name
    }

}
