package com.productosapp.database

import com.productosapp.entities.Products
import com.productosapp.entities.User

interface ProductSource {

    suspend fun loadProductById(userid : Int): MutableList<Products?>?
    suspend fun setDetail(id: Int)
    suspend fun insertProduct(product: Products)
    suspend fun getProductId(): Int
    suspend fun findProductDetail(): Products?
    suspend fun delete(product: Products?)
    suspend fun clearDetail(id: Int)


}