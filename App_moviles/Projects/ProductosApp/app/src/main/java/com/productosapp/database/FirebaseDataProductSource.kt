package com.productosapp.database

import com.google.firebase.firestore.Query
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

    var productListFb = mutableListOf<Products>()
    var productFb : Products = Products()

    override suspend fun loadProductByUserId(userid: Int){
        val productList = mutableListOf<Products>()
        val querySnapshot = collection
            .whereEqualTo("userid", userid)
            .get()
            .await()

        if(!querySnapshot.isEmpty){
            for (document in querySnapshot.documents) {
                val product = document.toObject<Products>()
                if (product != null) {
                    productList.add(product)
                } else {
                    throw IllegalStateException("No document found with the specified ID")
                }
                productListFb =productList
            }
        }
        else{
            productListFb = productList
        }
    }
    override suspend fun findProductDetail(): Products {
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
        val querySnapshot = collection
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return if (querySnapshot.isEmpty) {0}
        else {
            val lastDocument = querySnapshot.documents.lastOrNull()
            val product = lastDocument?.toObject<Products>()
            (product?.id?.plus(1)) ?: 0

        }
    }
    override suspend fun insertProduct(product: Products) {
        try {
            productListFb.add(product)
            collection.add(product).await()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert product into Firestore: ${e.message}")
        }
    }
    override suspend fun delete(id : Int) {
        try{
            val querySnapshot = collection
                .whereEqualTo("id",id)
                .get()
                .await()
            val documentRef = querySnapshot
                .first()
                .reference
                .delete()
                .await()

        }catch (e: Exception){
            throw IllegalStateException("Failed to remove product into Firestore: ${e.message}")
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



