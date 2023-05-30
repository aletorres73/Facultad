package com.productosapp.entities


import androidx.room.Entity
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@Entity(tableName = "imageproducts")
class Image(
    id      :   Int,
    item    :   String,
    imageUri:   String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int

    @ColumnInfo("item")
    var item: String

    @ColumnInfo("imageuri")
    var imageUri: String

    init{
        this.item       = item
        this.imageUri   = imageUri
        this.id         = id
    }
}


