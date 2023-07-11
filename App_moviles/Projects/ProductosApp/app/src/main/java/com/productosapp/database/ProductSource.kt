package com.productosapp.database

import android.net.Uri
import com.productosapp.entities.Products
import com.productosapp.entities.User
import java.net.URI

interface ProductSource {

    suspend fun loadProductByUserId(userid : String)
    suspend fun setDetail(id: Int)
    suspend fun insertProduct(product: Products)
    suspend fun getProductId(): Int
    suspend fun findProductDetail(): Products?
    suspend fun delete(id: Int)
    suspend fun clearDetail(id: Int)
    suspend fun uploadImage(path : Uri)
    suspend fun deleteImage(path: Uri)


}