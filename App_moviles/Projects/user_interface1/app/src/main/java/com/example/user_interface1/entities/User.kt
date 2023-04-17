package com.example.user_interface1.entities

class User(
    var name:       String,
    var lastname:   String,
    var email:      String,
    var password:   String ): Saludar {

    override fun saludar(): String {
        return "Hola, mi nombre es: $name $lastname"
    }


}
