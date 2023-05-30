package com.productosapp.entities


import androidx.room.Entity
import androidx.room.ColumnInfo

@Entity(tableName = "imageproducts")
class Image(
    item    :   String,
    imageUri:   String){

    @ColumnInfo("id")
    var item: String

    @ColumnInfo("imageuri")
    var imageUri: String

    init{
        this.item =     item
        this.imageUri=  imageUri

    }
}


