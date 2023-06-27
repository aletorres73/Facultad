package com.productosapp.fragments.user

import android.content.Context
import androidx.lifecycle.ViewModel
import com.productosapp.entities.User

class EditUserViewModel : ViewModel() {

    private var db: AppDataBase? = null
    private var userDao: UserDao? = null
    fun instanceDataBase(context: Context) {
        db = AppDataBase.getInstance(context)
        userDao = db?.UserDao()
        userDao?.loadAllUsers()
    }

    fun getUserLogged(): User? {
        return userDao?.findUserLogged()
    }

    fun updateAndSetUser(user: User){
        userDao?.update(user)
        userDao?.setLogged(user.id)

    }
}