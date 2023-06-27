package com.productosapp.database

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.User
import kotlinx.coroutines.tasks.await

class FirebaseDataUserSource (): UserSource{

    val db = Firebase.firestore
    private val collection = db.collection("users")

    override suspend fun getLoggedUser(): User {
        val querySnapshot = collection.whereEqualTo("logged", true).get().await()
        if (!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents.first()
            val user = userDocument.toObject<User>()
            return user ?: throw IllegalStateException("User is null")
        } else {
            throw IllegalStateException("No logged user found")
        }
    }

    override suspend fun getRegisteredUser(username: String, password: String): Boolean {
        val querySnapshot = collection
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .await()

        return !querySnapshot.isEmpty
    }

}