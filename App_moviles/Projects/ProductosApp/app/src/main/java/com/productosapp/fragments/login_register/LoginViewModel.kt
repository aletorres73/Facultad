package com.productosapp.fragments.login_register


import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
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

    private val userSource : FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)
    @SuppressLint("StaticFieldLeak")
    lateinit var requireActivity : FragmentActivity

    fun isLoginEmpty() {
        checkEmpty.value = username.value!!.isEmpty() || password.value!!.isEmpty()
    }
    fun isLoginOk() {
        viewModelScope.launch () {
            userSource.sigIn(username.value!!, password.value!!, requireActivity)
            if(userSource.currentUser){
                userSource.getRegisteredUser(username.value!!)
                userDb.value = userSource.userFb
                checkLogin.value = true
            }else{
                checkLogged.value = false
            }
        }
   }
    fun init(activity: FragmentActivity){
        requireActivity = activity
        viewModelScope.launch (){
            userSource.init()
        }
    }
}
