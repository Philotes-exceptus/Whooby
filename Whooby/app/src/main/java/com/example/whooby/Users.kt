package com.example.whooby

class User {
    var regd: String?= null
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var pass: String? = null

    constructor() {

    }

    constructor(regd : String? , name: String?, email:String?,uid: String?,pass: String?) {
        this.regd = regd
        this.name = name
        this.email = email
        this.uid = uid
        this.pass=pass
    }
}
