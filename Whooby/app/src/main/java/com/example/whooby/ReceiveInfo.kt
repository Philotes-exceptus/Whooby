package com.example.whooby

class ReceiveInfo {
    private lateinit var gettext : String


    public fun receiveinputtext(): String {
        return gettext
    }

    public fun setreceivetext(inputtext: String){
        this.gettext = gettext
    }
}