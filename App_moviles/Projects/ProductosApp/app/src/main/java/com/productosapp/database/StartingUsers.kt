package com.productosapp.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class StartingUsers (private val context: Context) : RoomDatabase.Callback(){

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingUsers","Pre-populating database...")
            fillWithStartingUsers(context)
        }
    }

    private fun fillWithStartingUsers(context: Context){
        val dao = AppDataBase.getInstance(context)?.UserDao()

        try {
            val users = loadJSONArray(context)
            for(i in 0 <= until < users.lenght)
        }
    }
}