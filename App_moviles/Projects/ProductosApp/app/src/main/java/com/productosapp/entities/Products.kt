package com.productosapp.entities

class Products(
    id    :      Int,
    userid:      Int,
    item:        String,
    detail:      Int,
    brand:       String,
    model:       String,
    costprice:   Int,
    sellingprice:Int,
    imageuri:    String){

    var id    : Int
    var userid: Int
    var item: String
    var brand: String
    var model: String
    var costprice: Int
    var sellingprice: Int
    var imageuri: String
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