package com.example.productapp.entities

object UserManager {
    private var users = mutableListOf<User>()

    fun getUsers(): List<User> {
        return users
    }
    fun addUser(user: User) {
        users.add(user)
    }
    fun removeUser(user: User) {
        users.remove(user)
    }
}

class User(
    var name:       String,
    var lastname:   String,
    var username:   String,
    var email:      String,
    var password:   String )


