package com.productosapp.fragments.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
class EditUserViewModel : ViewModel() {

    private val userSource : FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)
    var user : MutableLiveData<User> = MutableLiveData()
    fun getUserLogged(){
        viewModelScope.launch (Dispatchers.Main){
            user.value = userSource.getLoggedUser()
        }
    }
    fun updateAndSetUser(){
        viewModelScope.launch (Dispatchers.Main){
            user.value?.let { userSource.update(it) }
            user.value?.let { userSource.setLogged(it.id) }
        }
    }
}