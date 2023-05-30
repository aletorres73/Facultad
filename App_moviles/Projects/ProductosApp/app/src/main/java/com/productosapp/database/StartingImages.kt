package com.productosapp.database

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.productosapp.R
import com.productosapp.entities.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONException
import java.io.BufferedReader


class StartingImages(private val context : Context) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Dispatchers.IO).launch{
            Log.d("StartingImages","Pre-populating database...")
            fillWithStartingImagesFromJson(context)
        }
    }

    private fun fillWithStartingImagesFromJson(context: Context){
        val dao = AppDataBase.getInstance(context)?.ImageDao()

        try {
            val images = loadJSONArray(context, R.raw.image)
            for (i in 0 until images.length()) {
                val item = images.getJSONObject(i)
                val image = Image(
                    item    = item.getString("item"),
                    imageUri= item.getString("imageUri")
                )

                dao?.insertImage(image)
            }
        } catch (e: JSONException) {
            Log.e("fillWithStartingNotes", e.toString())
        }
    }

    private fun loadJSONArray(context: Context, file :Int) :JSONArray{
        val inputStream = context.resources.openRawResource(file)

        BufferedReader(inputStream.reader()).use {
            return JSONArray(it.readText())
        }
    }
}