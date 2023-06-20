package com.productosapp.fragments.login_register

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao



class LoginViewModel : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()


    private var db      : AppDataBase? = null
    private var userDao : UserDao?     = null

    val usernameDb = userDao?.loadUserByUsername(username.value!!)
    val passwordDb = userDao?.loadUserByPassword(password.value!!)

    fun instanceDataBase(context: Context){
        db = AppDataBase.getInstance(context)
        userDao = db?.UserDao()
        userDao?.loadAllUsers()
    }

    fun findUserLogged(): Boolean {
        return userDao?.findUserLogged() != null
    }

    fun isLoginEmpty(): Boolean{
        return username.value!!.isEmpty() || password.value!!.isEmpty()
    }

    fun isLoginOk(): Boolean{
        if(usernameDb?.username == username.value && passwordDb?.password == password.value){

            userDao?.setLogged(usernameDb!!.id)
            return true
        }
        return false
    }
}