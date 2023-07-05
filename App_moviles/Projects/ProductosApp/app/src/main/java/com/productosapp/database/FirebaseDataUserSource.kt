package com.productosapp.database

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.User
import com.productosapp.entities.userModule
import kotlinx.coroutines.tasks.await
import java.util.concurrent.locks.Condition

class FirebaseDataUserSource (): UserSource{


    val db = Firebase.firestore
    private val collectionName : String = "users"
    private val collection = db.collection(collectionName)

    var userFb : User? = User()

    override suspend fun getLoggedUser(){
        val querySnapshot = collection
            .whereEqualTo("logged", true)
            .get()
            .await()

        val userDocument = querySnapshot.documents.firstOrNull()
        userFb = userDocument?.toObject()
    }
    override suspend fun getRegisteredUser(username: String, password: String){
        val querySnapshot = collection
            .whereEqualTo("username", username)
            .whereEqualTo("password", password)
            .get()
            .await()

        val userDocument = querySnapshot.documents.firstOrNull()
        userFb = userDocument?.toObject<User>()
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
        val querySnapshot = collection
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return if (querySnapshot.isEmpty) {0}
        else {
            val lastDocument = querySnapshot.documents.lastOrNull()
            val user = lastDocument?.toObject<User>()
            (user?.id?.plus(1)) ?: 0
        }
    }
    override suspend fun insertUser(user: User) {
        try {
            collection.add(user).await()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert user into Firestore: ${e.message}")
        }
    }
    override suspend fun clearLoggedUser(id: Int, condition: Boolean) {
        val querySnapshot = collection
            .whereEqualTo("id",id)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents.first()
            userDocument
                .reference
                .update("logged",condition)
                .await()
        }else{
            throw IllegalStateException("User not found in clear logged user")
        }

        }
    override suspend fun setLogged(id: Int) {
        val querySnapshot = collection
            .whereEqualTo("id",id)
            .get()
            .await()

        if(!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents.first()
            userDocument
                .reference
                .update("logged", true)
                .await()
        }
        else{
            throw IllegalStateException("Error in clear logged user")
        }    }
    override suspend fun update(user: User) {
        val querySnapshot = collection
            .whereEqualTo("id", user.id)
            .get()
            .await()
        if(!querySnapshot.isEmpty){
            try {
                querySnapshot
                    .documents.first()
                    .reference
                    .set(user)
                    .await()
                println("User updated successfully")
            } catch (e: Exception) {
                throw IllegalStateException("Error updating user: ${e.message}")
            }
        }
    }
    override suspend fun getLoggedUserById(id: Int) {
        val querySnapshot = collection
            .whereEqualTo("id", id)
            .get()
            .await()

        val userDocument = querySnapshot.documents.firstOrNull()
        userFb = userDocument?.toObject()
    }
}