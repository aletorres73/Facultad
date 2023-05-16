package com.example.user_interface1.entities

object UserManager {
    private val users = mutableListOf(
        User(name = "Lio", lastname = "Messi", email = "lio@scaloneta.com", password = "lio"),
        User(name = "Angel", lastname = "Di Maria", email = "angel@scaloneta.com", password = "angel"),
        User(name = "Leandro", lastname = "Paredes", email = "leandro@scaloneta.com", password = "leandro"),
        User(name = "Dibu", lastname = "Martinez", email = "dibu@scaloneta.com", password = "dibu"),
        User(name = "Papu", lastname = "Gomez", email = "papu@scaloneta.com", password = "papu"),
        User(name = "Rodrigo", lastname = "De Paul", email = "rodrigo@scaloneta.com", password = "rodrigo")
    )
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
    var email:      String,
    var password:   String ): Saludar {

    override fun saludar(): String {
        return "Hola, mi nombre es: $name $lastname"
    }
}
