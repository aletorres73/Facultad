package com.productosapp.database

import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.Products
import com.productosapp.entities.User
import kotlinx.coroutines.tasks.await
import kotlin.system.measureTimeMillis

class FirebaseDataProductSource (): ProductSource {

    val db = Firebase.firestore
    private val colecctionName : String = "products"
    private val collection = db.collection(colecctionName)

    override suspend fun loadProductById(userid: Int): MutableList<Products?> {
        val querySnapshot = collection.whereEqualTo("id", userid).get().await()
        val productList = mutableListOf<Products?>()

        for (document in querySnapshot.documents) {
            val product = document.toObject<Products>()
            if (product != null) {
                productList.add(product)
            } else {
                throw IllegalStateException("No document found with the specified ID")
            }
        }
        return productList
    }
    override suspend fun findProductDetail(): Products? {
        val querySnapshot = collection
            .whereEqualTo("detail", true)
            .get()
            .await()
        if(!querySnapshot.isEmpty){
            val productDocument = querySnapshot.documents.first()
            val product = productDocument.toObject<Products>()
            return product ?: throw IllegalStateException("Product is null")
        }else{
            throw IllegalStateException("No product detail")
        }
    }
    override suspend fun getProductId(): Int {
        val querySnapshot = collection.get().await()

        return if (querySnapshot.isEmpty) {0}
        else {
            val documents = querySnapshot.documents
            val lastDocument = documents[documents.size - 1]
            val product = lastDocument.toObject<Products>()
            product?.id ?: throw IllegalStateException("User ID is null")
        }
    }
    override suspend fun insertProduct(product: Products) {
        try {
            collection.add(product).await()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert product into Firestore: ${e.message}")
        }    }
    override suspend fun delete(product: Products?) {
        if (product != null) {
            try{
                val querySnapshot = collection
                    .whereEqualTo("id",product.id)
                    .get()
                    .await()
                val documentRef = querySnapshot.documents.first()
                documentRef
                    .reference
                    .delete()
                    .await()
            }catch (e: Exception){
                throw IllegalStateException("Failed to remove user into Firestore: ${e.message}")
            }
        }else{
            throw IllegalArgumentException("Product cannot be null")
        }
    }
    override suspend fun setDetail(id: Int) {
        val querySnapshot = collection
            .whereEqualTo("id", id)
            .get()
            .await()
        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.first()
            document.reference.update("detail", 1).await()
        } else {
            throw IllegalStateException("No document found with the specified ID")
        }
    }
    override suspend fun clearDetail(id: Int) {
        val querySnapshot = collection
            .whereEqualTo("id",id)
            .get()
            .await()
        if (!querySnapshot.isEmpty) {
            val document = querySnapshot.first()
            document.reference.update("detail", 0).await()
        } else {
            throw IllegalStateException("No document found with the specified ID")
        }
    }
}



