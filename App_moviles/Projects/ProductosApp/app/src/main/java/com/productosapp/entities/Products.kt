package com.productosapp.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
class Products(
    id:          Int,
    userid:      Int,
    item:        String,
    detail:      Int,
    brand:       String,
    model:       String,
    costprice:   Int,
    sellingprice:Int,
    imageuri:    String){

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int

    @ColumnInfo("userid")
    var userid: Int

    @ColumnInfo("item")
    var item: String

    @ColumnInfo("brand")
    var brand: String

    @ColumnInfo("model")
    var model: String

    @ColumnInfo("costprice")
    var costprice: Int

    @ColumnInfo("sellingprice")
    var sellingprice: Int

    @ColumnInfo("imageuri")
    var imageuri: String

    @ColumnInfo("detail")
    var detail: Int

    init{
        this.id           = id
        this.userid       = userid
        this.item         = item
        this.detail       = detail
        this.brand        = brand
        this.model        = model
        this.costprice    = costprice
        this.sellingprice = sellingprice
        this.imageuri    =  imageuri
    }
}