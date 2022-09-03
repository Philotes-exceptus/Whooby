package com.example.whooby

import java.util.*

class User {
    var regd: String? = null
    var name: String? = ""
    var inputtext: String? = ""
    val msg_queue: Queue<String> = LinkedList()


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
        msg_queue.add(inputtext)
        return inputtext
    }

    @JvmName("getName1")
    fun getName(): String? {
        return name
    }

    fun messagePopulate(): Queue<String> {
        return msg_queue
    }

}
