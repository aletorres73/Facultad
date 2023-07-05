package com.productosapp.database

import com.productosapp.entities.User
import java.util.concurrent.locks.Condition

interface UserSource {

    suspend fun getLoggedUser()
    suspend fun getRegisteredUser(username : String, password : String)
    suspend fun loadUserByUsername(username: String) : User
    suspend fun getUserId(): Int
    suspend fun insertUser(user: User)
    suspend fun clearLoggedUser(id: Int, condition: Boolean)
    suspend fun setLogged(id: Int)
    suspend fun update(user: User)
    suspend fun getLoggedUserById(id: Int)
}