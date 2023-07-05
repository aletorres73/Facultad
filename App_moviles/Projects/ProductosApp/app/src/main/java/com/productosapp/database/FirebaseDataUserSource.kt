package com.productosapp.database


import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.User
import kotlinx.coroutines.tasks.await

class FirebaseDataUserSource (): UserSource {

    val db = Firebase.firestore
    private val collectionName: String = "users"
    private val collection = db.collection(collectionName)

    private lateinit var auth: FirebaseAuth
    var userFb: User? = User()
    var currentUser: Boolean = false

    override suspend fun getLoggedUser() {
        val querySnapshot = collection
            .whereEqualTo("logged", true)
            .get()
            .await()

        val userDocument = querySnapshot.documents.firstOrNull()
        userFb = userDocument?.toObject()
    }

    //    override suspend fun getRegisteredUser(username: String, password: String){
    override suspend fun getRegisteredUser(username: String) {
        val querySnapshot = collection
            .whereEqualTo("email", username)
//            .whereEqualTo("password", password)
            .get()
            .await()

        if (querySnapshot.isEmpty) {
            userFb = null
        }
        else {
            val userDocument = querySnapshot.documents.firstOrNull()
            userFb = userDocument?.toObject<User>()
        }
    }

    override suspend fun loadUserByUsername(username: String): User {
        val querySnapshot = collection.whereEqualTo("username", username).get().await()
        if (!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents.first()
            val user = userDocument.toObject<User>()
            return user ?: throw IllegalStateException("User is null")
        } else {
            throw IllegalStateException("No user found by ID")
        }
    }

    override suspend fun getUserId(): Int {
        val querySnapshot = collection
            .orderBy("id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .await()

        return if (querySnapshot.isEmpty) {
            0
        } else {
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
            .whereEqualTo("id", id)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents.first()
            userDocument
                .reference
                .update("logged", condition)
                .await()
        } else {
            throw IllegalStateException("User not found in clear logged user")
        }

    }

    override suspend fun setLogged(id: Int) {
        val querySnapshot = collection
            .whereEqualTo("id", id)
            .get()
            .await()

        if (!querySnapshot.isEmpty) {
            val userDocument = querySnapshot.documents.first()
            userDocument
                .reference
                .update("logged", true)
                .await()
        } else {
            throw IllegalStateException("Error in clear logged user")
        }
    }

    override suspend fun update(user: User) {
        val querySnapshot = collection
            .whereEqualTo("id", user.id)
            .get()
            .await()
        if (!querySnapshot.isEmpty) {
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

    override suspend fun sigIn(email: String, password: String, activity: FragmentActivity) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser != null) {
                        currentUser = true
                    }
                } else {
                    currentUser = false
                }
            }.await()
    }

    override suspend fun init() {
        auth = Firebase.auth
    }
}