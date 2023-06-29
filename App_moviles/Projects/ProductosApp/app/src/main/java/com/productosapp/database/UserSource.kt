package com.productosapp.database

import com.productosapp.entities.User

interface UserSource {

    suspend fun getLoggedUser(): User?
    suspend fun getRegisteredUser(username : String, password : String): User?
    suspend fun loadUserByUsername(username: String) : User
    suspend fun getUserId(): Int
    suspend fun insertUser(user: User)
    suspend fun clearLoggedUser(id: Int)
    suspend fun setLogged(id: Int)
    suspend fun update(user: User)
}