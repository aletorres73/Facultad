package com.productosapp.fragments.login_register


import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.database.UserSource
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.ERROR_MSG
import org.koin.java.KoinJavaComponent.inject

class RegisterViewModel: ViewModel() {

    private val userSource: UserSource by inject(FirebaseDataUserSource::class.java)

    var userDb: MutableLiveData<User> = MutableLiveData()
    var viewState : MutableLiveData<String> = MutableLiveData()

    companion object{
        const val STATE_CREATING = "creating"
        const val STATE_DONE = "done"
        const val STATE_ERROR = "error"
    }

    fun checkEmptyUser(){
        val chekError =   userDb.value?.name.isNullOrEmpty()        ||
                          userDb.value?.lastname.isNullOrEmpty()    ||
                          userDb.value?.email.isNullOrEmpty()       ||
                          userDb.value?.password.isNullOrEmpty()    ||
                          userDb.value?.username.isNullOrEmpty()
        if (!chekError){
            viewState.value = STATE_CREATING
        }else{
            viewState.value = STATE_ERROR
        }
    }
    fun createUser(activity: FragmentActivity){
        viewModelScope.launch(Dispatchers.Main) {
            val id = userSource.getUserId().toString()
            userSource.createAccount(userDb.value?.email!!, userDb.value?.password!!, activity)
            userDb.value?.let {
                userSource.insertUser(it, id)
                viewState.value = STATE_DONE
            }
        }
    }
}