package com.example.user_interface1.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.user_interface1.R
import com.example.user_interface1.entities.User

class MainActivity : AppCompatActivity() {

    var users : MutableList<User> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        users.add(User(name = "Lio", lastname = "Messi", email = "Lio@scaloneta.com", password = "Lio"))
        users.add(User(name = "Angel", lastname = "Di Maria", email = "Angel@scaloneta.com", password = "Angel"))
        users.add(User(name = "Leandro", lastname = "Paredes", email = "Leandro@scaloneta.com", password = "Leandro"))
        users.add(User(name = "Dibu", lastname = "Martinez", email = "Dibu@scaloneta.com", password = "Dibu"))
        users.add(User(name = "Papu", lastname = "Gomez", email = "Papu@scaloneta.com", password = "Papu"))
        users.add(User(name = "Rodrigo", lastname = "De Paul", email = "Rodrigo@scaloneta.com", password = "Rodrigo"))

        users.forEach{currentUser ->
            Log.d("MainActivity", currentUser.saludar())}

    }
}