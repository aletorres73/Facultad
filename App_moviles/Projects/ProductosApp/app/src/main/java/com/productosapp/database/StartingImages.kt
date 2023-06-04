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
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("StartingImages", "Pre-populating database...")
            fillWithStartingImages(context)
        }
    }

    private fun fillWithStartingImages(context: Context) {
        val images = listOf(
            Image(
                0, "termo",
                "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F37101adf-ef31-4f5e-980b-2cc8c67d8b2f%2F002.jpg?id=01c7c0fe-7be7-4dc7-aba2-aaab25be7446&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
            ),
            Image(
                0, "pava",
                "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1ae4fd3a-ce7f-4146-9d9c-7c413adc8f06%2F003.jpg?id=28e27ca3-cffd-4422-b099-ba33b812610e&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
            ),
            Image(
                0, "horno",
                "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F790ff253-500c-4597-8ba3-0aacbb0abe62%2F001.jpg?id=2ee578f8-124c-4b87-99bb-403690e7c63c&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
            ),
            Image(
                0, "anafe",
                "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ffcdd389f-4fa6-4aae-b94a-06645c646e51%2F003.jpg?id=9a13b4c9-8194-48c9-9105-cf971f37bf7a&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
            )
        )
        val dao = AppDataBase.getInstance(context)?.ImageDao()

        images.forEach {
            dao?.insertImage(it)
        }
    }
}

//    private fun fillWithStartingImagesFromJson(context: Context){
//        val dao = AppDataBase.getInstance(context)?.ImageDao()
//
//        try {
//            val images = loadJSONArray(context, R.raw.image)
//            for (i in 0 until images.length()) {
//                val item = images.getJSONObject(i)
//                val image = Image(
//                    item    = item.getString("item"),
//                    imageUri= item.getString("imageUri")
//                )
//
//                dao?.insertImage(image)
//            }
//        } catch (e: JSONException) {
//            Log.e("fillWithStartingNotes", e.toString())
//        }
//    }
//
//    private fun loadJSONArray(context: Context, file :Int) :JSONArray{
//        val inputStream = context.resources.openRawResource(file)
//
//        BufferedReader(inputStream.reader()).use {
//            return JSONArray(it.readText())
//        }
//    }
//}