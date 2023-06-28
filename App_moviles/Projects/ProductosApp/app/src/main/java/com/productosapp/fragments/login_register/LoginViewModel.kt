package com.productosapp.fragments.login_register


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataUserSource
import kotlinx.coroutines.*
import org.koin.java.KoinJavaComponent.inject
class LoginViewModel : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    private var _checkView: MutableLiveData<Boolean> = MutableLiveData()
    val checkView : LiveData<Boolean> get()= _checkView

    private val userSource : FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)

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
            _checkView.value = userSource.getRegisteredUser(username.value!! , password.value!!)
        }
   }
}
