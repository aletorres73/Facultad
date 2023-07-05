package com.productosapp.fragments.login_register


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.database.UserSource
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class RegisterViewModel: ViewModel() {

    private val userSource: UserSource by inject(FirebaseDataUserSource::class.java)

    var userDb: MutableLiveData<User> = MutableLiveData()
    var chekError:  MutableLiveData<Boolean> = MutableLiveData()
//    fun loadUserToRepository() {
//        viewModelScope.launch(Dispatchers.Main) {
//            userDb.value?.let { userSource.insertUser(it) }
//        }
//    }
    fun checkEmptyUser(){
        chekError.value = userDb.value?.name.isNullOrEmpty() ||
                          userDb.value?.lastname.isNullOrEmpty() ||
                          userDb.value?.email.isNullOrEmpty() ||
                          userDb.value?.password.isNullOrEmpty() ||
                          userDb.value?.username.isNullOrEmpty()
    }
    fun createUser(){
        viewModelScope.launch(Dispatchers.Main) {
            val id = userSource.getUserId()
            userDb.value?.id = id
            userDb.value?.let { userSource.insertUser(it) }
        }
    }
}