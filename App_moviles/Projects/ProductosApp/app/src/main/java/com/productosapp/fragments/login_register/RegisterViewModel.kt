package com.productosapp.fragments.login_register


import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.productosapp.database.ProductSource
import com.productosapp.database.UserSource
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel(private val userSource: UserSource, private val productSource: ProductSource): ViewModel() {

    val db = Firebase.firestore

    val name    : MutableLiveData<String> = MutableLiveData()
    val lastname: MutableLiveData<String> = MutableLiveData()
    val email   : MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()
    val username: MutableLiveData<String> = MutableLiveData()

    private lateinit var userDb : User

    fun loadUser() {
        viewModelScope.launch(Dispatchers.Main) {
            userDb = userSource.loadUserByUsername(username.value!!)
        }

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
        viewModelScope.launch(Dispatchers.Main) {
            val id = userSource.getUserId()
            val user = User(
                id = id,
                logged = false,
                name = name.value!!,
                lastname = lastname.value!!,
                username = username.value!!,
                email = email.value!!,
                password = password.value!!
            )
            userSource.insertUser(user)
        }
    }
}