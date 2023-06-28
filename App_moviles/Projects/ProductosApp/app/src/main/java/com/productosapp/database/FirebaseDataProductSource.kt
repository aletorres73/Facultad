package com.productosapp.database

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.Products
import com.productosapp.entities.User
import kotlinx.coroutines.tasks.await

class FirebaseDataProductSource (): ProductSource {

    val db = Firebase.firestore
    private val collection = db.collection("products")

    override suspend fun loadProductById(userid: Int): MutableList<Products?>? {
        val querySnapshot = collection.whereEqualTo("id", userid).get().await()
        val productList = mutableListOf<Products?>()

        for (document in querySnapshot.documents) {
            val product = document.toObject(Products::class.java)
            if (product != null) {
                productList.add(product)
            } else {
                throw IllegalStateException("No document found with the specified ID")
            }
        }
        return productList
    }

    override suspend fun setDetail(id: Int) {
        val querySnapshot = collection.whereEqualTo("id", id).get().await()

        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.first()
            document.reference.update("detail", 1).await()
        } else {
            throw IllegalStateException("No document found with the specified ID")
        }

    }

    override suspend fun insertProduct(product: Products) {
        TODO("Not yet implemented")
    }

    override suspend fun getProductId(): Int {
        TODO("Not yet implemented")
    }

    override suspend fun findProductDetail(): Products? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(product: Products?) {
        TODO("Not yet implemented")
    }

    override suspend fun clearDetail(id: Int) {
        TODO("Not yet implemented")
    }
}



