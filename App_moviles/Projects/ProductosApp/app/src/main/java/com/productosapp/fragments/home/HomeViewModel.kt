package com.productosapp.fragments.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.productosapp.database.*
import com.productosapp.entities.Products
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel : ViewModel() {

    var userDb: MutableLiveData<User> = MutableLiveData()
    var productDB: MutableLiveData<MutableList<Products?>> = MutableLiveData()
    private lateinit var productDb: MutableList<Products?>

    private val userSource: FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)
    private val productSource: FirebaseDataProductSource by inject(FirebaseDataProductSource::class.java)

    fun getUserProduct() {
        viewModelScope.launch (Dispatchers.Main) {
            userDb.value = userSource.getLoggedUser()
            productDB.value = userDb.value?.let { productSource.loadProductById(it.id)}
        }
    }
//    fun getListProduct(){
//        viewModelScope.launch(Dispatchers.Main) {
//            productDB.value = userDb.value?.let { productSource.loadProductById(it.id) }
//        }
//    }
    fun setDetailProduct(product: Products){
        viewModelScope.launch(Dispatchers.Main) {
            productSource.setDetail(product.id)
        }
    }
}