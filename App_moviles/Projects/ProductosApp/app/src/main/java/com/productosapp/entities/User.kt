package com.productosapp.entities

class User(
    id: Int,
    logged: Boolean,
    name: String,
    lastname: String,
    username: String,
    email: String,
    password: String
) {
    var password: String
    var email: String
    var username: String
    var lastname: String
    var name: String
    var logged: Boolean
    var id: Int

    init {
        this.id = id
        this.logged = logged
        this.name = name
        this.lastname = lastname
        this.username = username
        this.email = email
        this.password = password
    }
}


