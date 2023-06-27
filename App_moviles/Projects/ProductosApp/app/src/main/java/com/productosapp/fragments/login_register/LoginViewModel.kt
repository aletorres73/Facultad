package com.productosapp.fragments.login_register

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao
import com.productosapp.entities.User
import kotlinx.coroutines.tasks.await
import java.util.Collections

class LoginViewModel : ViewModel() {

    val username: MutableLiveData<String> = MutableLiveData()
    val password: MutableLiveData<String> = MutableLiveData()

    //    private var db: AppDataBase? = null
//    private var userDao: UserDao? = null
    private lateinit var user: User

    private val db = Firebase.firestore
    private val usersDb = db.collection("users")

//    fun instanceDataBase(context: Context) {
////        db = AppDataBase.getInstance(context)
////        userDao = db?.UserDao()
////        userDao?.loadAllUsers()
//        usersDb.get()
//            .addOnSuccessListener { document ->
//                if (document != null) {
//                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
//                } else {
//                    Log.d(TAG, "No such document")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d(TAG, "get failed with ", exception)
//            }
//    }
    suspend fun checkLoggedCondition(): Boolean {
        val querySnapshot = usersDb
            .whereEqualTo("logged", true)
            .get()
            .await()

        return !querySnapshot.isEmpty
    }



    fun isLoginEmpty(): Boolean {
        //return username.value.isNullOrEmpty() || password.value.isNullOrEmpty()
    }

    fun isLoginOk(): Boolean {
//        if (userDb?.username == username.value && userDb?.password == password.value) {
//            userDao?.setLogged(userDb!!.id)
//            return true
//        }
//        return false
//    }
}
