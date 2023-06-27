package com.productosapp.database

import com.productosapp.entities.Products
import com.productosapp.entities.User

interface ProductSource {

    suspend fun getUserProduct() : User?
    suspend fun loadProductById(id : Int): MutableList<Products?>?
    suspend fun setDetail(id: Int)


}