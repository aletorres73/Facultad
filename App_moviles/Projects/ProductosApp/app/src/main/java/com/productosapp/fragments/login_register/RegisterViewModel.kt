package com.productosapp.fragments.login_register


import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao
import com.productosapp.entities.User

class RegisterViewModel : ViewModel() {

    val name    : MutableLiveData<String> = MutableLiveData()
    val lastname: MutableLiveData<String> = MutableLiveData()
    val email   : MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()

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

    fun checkEmptyUser(): Boolean{

        if (name.value!!.isEmpty()) {
           return true
        } else if (lastname.value!!.isEmpty()) {
            return true
        } else if (email.value!!.isEmpty()) {
            return true
        } else if (password.value!!.isEmpty()) {
            return true
        } else if (username.value!!.isEmpty()) {
            return true
        }
        return false
    }

    fun createUser(){

        val user = User(
            id       = 0,
            logged   = 0,
            name     = name.value!!,
            lastname = lastname.value!!,
            username = username.value!!,
            email    = email.value!!,
            password = password.value!!
        )
        userDao?.insertUser(user)
    }
}