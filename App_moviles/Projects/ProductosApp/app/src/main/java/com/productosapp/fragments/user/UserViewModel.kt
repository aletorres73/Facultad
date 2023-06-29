package com.productosapp.fragments.user

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
class UserViewModel : ViewModel() {

    private val userSource: FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)
    var user : MutableLiveData<User> = MutableLiveData()

    fun getUserLogged(): Boolean{
        viewModelScope.launch(Dispatchers.Main){
           user.value= userSource.getLoggedUser()
        }
        return user.value!=null
    }
    fun clearUserLogged(id : Int){
        viewModelScope.launch(Dispatchers.Main){
            userSource.clearLoggedUser(id)
        }
    }
}