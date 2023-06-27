package com.productosapp.fragments.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.productosapp.database.*
import com.productosapp.entities.Products
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel (private val userSource: UserSource, private val productSource: ProductSource): ViewModel() {

    val db = Firebase.firestore
    lateinit var userDb: User
    private lateinit var productDb: MutableList<Products?>

    fun getUserProduct(): User?{
        viewModelScope.launch (Dispatchers.Main) {
             userDb = productSource.getUserProduct()!!
        }
        return userDb
    }

    fun getListProduct(user: User): MutableList<Products?>? {
        viewModelScope.launch(Dispatchers.Main) {
            productDb = productSource.loadProductById(user.id)!!
        }
        return productDb
    }


    fun setDetailProduct(product: Products){
        viewModelScope.launch(Dispatchers.Main) {
            productSource.setDetail(product.id)
        }
    }
}