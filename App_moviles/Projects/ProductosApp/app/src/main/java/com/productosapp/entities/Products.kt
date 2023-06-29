package com.productosapp.entities

class Products(){
    var id    :      Int = 0
    var userid:      Int = 0
    var item:        String=""
    var detail:      Int=0
    var brand:       String=""
    var model:       String=""
    var costprice:   Int=0
    var sellingprice:Int=0
    var imageuri:    String=""

    constructor (
    id    : Int,
    userid: Int,
    item: String,
    detail: Int,
    brand: String,
    model: String,
    costprice: Int,
    sellingprice: Int,
    imageuri: String,
    ):this(){
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