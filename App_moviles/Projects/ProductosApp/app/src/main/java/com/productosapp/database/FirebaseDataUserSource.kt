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
    override suspend fun loadUserByUsername(username: String): User {
        val querySnapshot = collection.whereEqualTo("username", username).get().await()
        if(!querySnapshot.isEmpty){
            val userDocument = querySnapshot.documents.first()
            val user = userDocument.toObject<User>()
            return user ?: throw IllegalStateException("User is null")
        } else{
            throw IllegalStateException("No user found by ID")
        }
    }
    override suspend fun getUserId(): Int {
        val querySnapshot = collection.get().await()

        return if (querySnapshot.isEmpty) {0}
        else {
            val documents = querySnapshot.documents
            val lastDocument = documents[documents.size - 1]
            val user = lastDocument.toObject<User>()
            user?.id ?: throw IllegalStateException("User ID is null")
        }
    }
    override suspend fun insertUser(user: User) {
        try {
            collection.add(user).await()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert user into Firestore: ${e.message}")
        }
    }

}