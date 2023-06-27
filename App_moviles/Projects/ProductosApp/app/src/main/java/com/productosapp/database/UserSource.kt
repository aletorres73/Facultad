package com.productosapp.database

import com.productosapp.entities.User

interface UserSource {

    suspend fun getLoggedUser(): User
    suspend fun getRegisteredUser(username : String, password : String): Boolean

}