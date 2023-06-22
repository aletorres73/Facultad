package com.productosapp.fragments.login_register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao
import com.productosapp.entities.User

class LoginViewModel : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private var db: AppDataBase? = null
    private var userDao: UserDao? = null
    private var userDb: User? = null

    fun instanceDataBase(context: Context) {
        db = AppDataBase.getInstance(context)
        userDao = db?.UserDao()
        userDao?.loadAllUsers()
    }

    fun loadUser(){
        userDb = userDao?.loadUserByUsername(username.value!!)
    }
    fun findUserLogged(): Boolean {
        return userDao?.findUserLogged() != null
    }

    fun isLoginEmpty(): Boolean {
        return username.value.isNullOrEmpty() || password.value.isNullOrEmpty()
    }

    fun isLoginOk(): Boolean {
        if (userDb?.username == username.value && userDb?.password == password.value) {
            userDao?.setLogged(userDb!!.id)
            return true
        }
        return false
    }
}
