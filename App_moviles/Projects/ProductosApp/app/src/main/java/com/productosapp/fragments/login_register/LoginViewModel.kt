package com.productosapp.fragments.login_register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.entities.User
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
class LoginViewModel : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    var checkEmpty: MutableLiveData<Boolean> = MutableLiveData()
    var checkLogin: MutableLiveData<Boolean> = MutableLiveData()
    var checkLogged: MutableLiveData<Boolean> = MutableLiveData()
    var userDb : MutableLiveData<User> = MutableLiveData()
//    val checkView : LiveData<Boolean> get()= _checkView

    private val userSource : FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)

    fun checkLoggedCondition() {
        viewModelScope.launch (Dispatchers.Main){
            userDb.value = userSource.getLoggedUser()
            checkLogged.value = userDb.value != null
        }
    }
    fun isLoginEmpty() {
        checkEmpty.value = username.value!!.isEmpty() || password.value!!.isEmpty()
    }
    fun isLoginOk() {
        viewModelScope.launch (Dispatchers.Main) {
            userDb.value = userSource.getRegisteredUser(username.value!! , password.value!!)
            checkLogin.value = userDb.value != null
        }
   }
    fun setLogged(){
        viewModelScope.launch(Dispatchers.Main ){
            userSource.setLogged(userDb.value!!.id)
        }
    }
}
