package com.productosapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.productosapp.entities.Image


@Dao
interface ImageDao {

    @Query("SELECT * FROM imageproducts ORDER BY item")
    fun loadAllImages(): MutableList<Image?>?

    @Insert
    fun insertImage (image: Image?)

    @Query("SELECT imageuri FROM imageproducts WHERE item= :item")
    fun getItem(item: String) : String?

}