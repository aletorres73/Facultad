package com.productosapp.database

import androidx.core.location.LocationRequestCompat.Quality
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.productosapp.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun loadAllUsers(): MutableList<User?>?

    @Insert
    fun updateUser(user: User?)

    @Update
    fun delete(user: User?)

    @Query("SELECT * FROM users WHERE id = :id")
    fun loadUserById(id: Int):User?
}