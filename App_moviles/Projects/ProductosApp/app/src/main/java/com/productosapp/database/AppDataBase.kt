package com.productosapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.productosapp.entities.Products
import com.productosapp.entities.User

@Database(entities =[User::class , Products::class], version = 1, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun ProductsDao(): ProductsDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AppDataBase?{
            if(INSTANCE == null){
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase (context: Context):AppDataBase?{
            if (INSTANCE == null){
                synchronized(AppDataBase::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java
                    )
                        .addCallback(StartingUsers(context))
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}