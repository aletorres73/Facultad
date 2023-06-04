package com.productosapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.productosapp.entities.Image
import com.productosapp.entities.Products
import com.productosapp.entities.User

@Database(entities =[User::class , Products::class, Image::class], version = 5, exportSchema = false)
abstract class AppDataBase: RoomDatabase() {

    abstract fun UserDao(): UserDao
    abstract fun ProductsDao(): ProductsDao
    abstract fun ImageDao():ImageDao

    companion object {
        private var INSTANCE: AppDataBase? = null

        @Synchronized
        fun getInstance(context: Context): AppDataBase?{
            if(INSTANCE == null){
                INSTANCE = buildDatabase(context)
            }
            return INSTANCE
        }

        private fun buildDatabase(context: Context): AppDataBase? {
            if (INSTANCE == null) {
                synchronized(AppDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "myDB"
                    )
                        .addCallback(StartingUsers(context))
                        .addCallback(StartingImages(context))
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries() // No es recomendable que se ejecute en el mainthread
                        .build()
                }
            }
            return INSTANCE
        }
    }
}