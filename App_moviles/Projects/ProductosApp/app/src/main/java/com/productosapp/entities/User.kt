package com.productosapp.entities

import android.os.Parcel
import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.material.circularreveal.CircularRevealHelper.Strategy
import kotlinx.parcelize.Parcelize

//object UserManager {
//    private var users = mutableListOf<User>()
//
//    init {
//        users.add(User(name = "ale", lastname = "torres", username = "ale",
//                        email = "torresalejandro7392@gmail.com", password = "1234"))
//    }
//
//    fun getUsers(): List<User> {
//        return users
//    }
//    fun addUser(user: User) {
//        users.add(user)
//    }
//    fun removeUser(user: User) {
//        users.remove(user)
//    }
//}
//
//@Parcelize
//class User(
//    var name:       String,
//    var lastname:   String,
//    var username:   String,
//    var email:      String,
//    var password:   String,) :Parcelable

@Entity(tableName = "users")
class User (id: Int, name: String, lastname: String, email: String, password: String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int

    @ColumnInfo("name")
    var name: String

    @ColumnInfo("lastname")
    var lastname: String

    @ColumnInfo("email")
    var email: String

    @ColumnInfo("password")
    var password: String

    init{
        this.id         = id
        this.name       = name
        this.lastname   = lastname
        this.email      = email
        this.password   = password
    }
}