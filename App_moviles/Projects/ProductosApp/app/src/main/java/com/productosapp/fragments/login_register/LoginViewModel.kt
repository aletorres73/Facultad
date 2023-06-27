package com.productosapp.fragments.login_register

import android.content.ContentValues.TAG
import android.content.Context
import android.text.BoringLayout
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao
import com.productosapp.database.UserSource
import com.productosapp.entities.User
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.util.Collections

class LoginViewModel (private val userSource: UserSource) : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private var _checkView: MutableLiveData<Boolean> = MutableLiveData()
    val checkView : LiveData<Boolean> get()= _checkView



    fun checkLoggedCondition() {
        viewModelScope.launch (Dispatchers.Main){
            val userLogged = userSource.getLoggedUser()
            _checkView.value = userLogged.logged
        }
    }

    fun isLoginEmpty() {
        _checkView.value = username.value!!.isEmpty() || password.value!!.isEmpty()
    }

    fun isLoginOk() {
        viewModelScope.launch (Dispatchers.Main) {
            val userRegistered = userSource.getRegisteredUser()
            _checkView.value =
                (username.value == userRegistered.username) && (password.value == userRegistered.password)
        }
   }
}
