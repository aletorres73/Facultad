package com.productosapp.database

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.productosapp.entities.User
import java.util.concurrent.locks.Condition

interface UserSource {

    suspend fun getRegisteredUser()
    suspend fun loadUserByUsername(username: String) : User
    suspend fun getUserId()
    suspend fun insertUser(user: User)
    suspend fun update(user: User)
    suspend fun getLoggedUserById()
    suspend fun sigIn(email: String, password: String, activity: FragmentActivity)
    suspend fun init()
    suspend fun createAccount(email: String, password: String, activity: FragmentActivity)
    suspend fun closeSestion(): Boolean
}