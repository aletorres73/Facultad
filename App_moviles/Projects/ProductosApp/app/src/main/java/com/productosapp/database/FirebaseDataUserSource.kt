package com.productosapp.database


import androidx.fragment.app.FragmentActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.User
import kotlinx.coroutines.tasks.await
import java.net.UnknownServiceException

class FirebaseDataUserSource (): UserSource {

    val db = Firebase.firestore
    private val collectionName: String = "users"
    private val collection = db.collection(collectionName)

    private lateinit var auth: FirebaseAuth
    var userFb: User? = User()
    var currentUser: Boolean = false

    lateinit var currentUID : String

    override suspend fun getRegisteredUser() {
        val querySnapshot = collection
            .document(currentUID)
            .get()
            .await()

        if (querySnapshot.exists() ) {
            userFb = null
        } else {
            userFb = querySnapshot.toObject<User>()
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
    override suspend fun getUserId() {
        val user = Firebase.auth.currentUser
        user?.let {
            currentUID =it.uid
        }
    }
    override suspend fun insertUser(user: User) {
        try {
            collection.document(currentUID).set(user).await()
        } catch (e: Exception) {
            throw IllegalStateException("Failed to insert user into Firestore: ${e.message}")
        }
    }
    override suspend fun update(user: User) {
       collection.document(currentUID)
           .update(
               mapOf(
                   "name" to user.name,
                   "lastname" to user.lastname,
                   "username" to user.username,
                   "email" to user.email,
                   ),
           )
           .await()
    }

    override suspend fun getLoggedUserById() {
        val userDocument = collection.document(currentUID).get().await()
        userFb = userDocument.toObject()
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
            }
    }
    override suspend fun init() {
        auth = Firebase.auth
    }

    override suspend fun createAccount(email: String, password: String, activity: FragmentActivity) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    if (auth.currentUser != null) {
                        currentUser = true
                    }
                } else {
                    currentUser = false
                }
            }
            .await()
    }
    override suspend fun closeSestion(): Boolean{
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
        val currentUser = auth.currentUser
        return currentUser == null
    }
}