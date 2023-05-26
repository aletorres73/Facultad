package com.productosapp.database

import androidx.core.location.LocationRequestCompat.Quality
import androidx.room.*
import com.productosapp.entities.User

@Dao
interface UserDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun loadAllUsers(): MutableList<User?>?

    @Insert
    fun insertUser(user: User?)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User?)

    @Query("SELECT * FROM users WHERE id = :id")
    fun loadUserById(id: Int):User?

    @Query("SELECT * FROM users WHERE username = :username")
    fun loadUserByUsername(username: String):User?

    @Query("SELECT * FROM users WHERE password = :password")
    fun loadUserByPassword(password: String):User?

    @Query("SELECT * FROM users WHERE logged = 1")
    fun findUserLogged(): User?

    @Query("UPDATE users SET logged = 1 WHERE id = :userId")
    fun setLogged(userId: Int)

    @Query("UPDATE users SET logged = 0 WHERE id = :userId")
    fun clearLogged(userId: Int?)

}