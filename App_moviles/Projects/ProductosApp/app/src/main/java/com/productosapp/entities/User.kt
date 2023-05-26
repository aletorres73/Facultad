package com.productosapp.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User (
    id:         Int,
    logged:     Int,
    name:       String,
    lastname:   String,
    username:   String,
    email:      String,
    password:   String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int

    @ColumnInfo("logged")
    var logged: Int

    @ColumnInfo("name")
    var name: String

    @ColumnInfo("lastname")
    var lastname: String

    @ColumnInfo("username")
    var username: String

    @ColumnInfo("email")
    var email: String

    @ColumnInfo("password")
    var password: String

    init{
        this.id         = id
        this.logged     = 0
        this.name       = name
        this.lastname   = lastname
        this.username   = username
        this.email      = email
        this.password   = password
    }
}

