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
    var userFb : MutableLiveData<User> = MutableLiveData()
    var userEdit : MutableLiveData<User> = MutableLiveData()
    fun getUserLogged(){
        viewModelScope.launch (Dispatchers.Main){
            userSource.getLoggedUser()
            userFb.value = userSource.userFb
        }
    }
    fun updateAndSetUser(){
        viewModelScope.launch (Dispatchers.Main){
            userEdit.value?.let { userSource.update(it) }
            userEdit.value?.let { userSource.setLogged(it.id) }
        }
    }
}