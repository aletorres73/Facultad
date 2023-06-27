package com.productosapp.database

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.Products
import com.productosapp.entities.User

class FirebaseDataProductSource (): ProductSource {

    val db = Firebase.firestore
    private val collection = db.collection("products")

    override suspend fun getUserProduct(): User? {
        TODO("Not yet implemented")
    }

    override suspend fun loadProductById(id: Int): MutableList<Products?>? {
        TODO("Not yet implemented")
    }

    override suspend fun setDetail(id: Int) {
        TODO("Not yet implemented")
    }


}