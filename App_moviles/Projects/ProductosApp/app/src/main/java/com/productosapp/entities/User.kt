package com.productosapp.entities

class User() {
    var email       : String = ""
    var username    : String = ""
    var lastname    : String = ""
    var name        : String = ""
    constructor(

        name: String,
        lastname: String,
        username: String,
        email: String,

    ) : this() {
        this.name = name
        this.lastname = lastname
        this.username = username
        this.email = email
    }
}
