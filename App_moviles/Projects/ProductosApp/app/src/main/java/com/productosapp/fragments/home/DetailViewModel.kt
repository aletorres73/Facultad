package com.productosapp.fragments.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.productosapp.database.AppDataBase
import com.productosapp.database.ProductsDao
import com.productosapp.entities.Products

class DetailViewModel : ViewModel() {

    private var db: AppDataBase? = null
    private var productDao: ProductsDao? = null

    fun instanceDataBase(context : Context){
        db = AppDataBase.getInstance(context)
        productDao = db?.ProductsDao()
        productDao?.loadAllProducts()
    }

    fun getProductDetail(): Products? {
        return productDao?.findProductDetail()
    }

    fun getProductImageUri(): String? {
        return productDao?.getImageUrl()
    }

    fun removeProduct(product: Products?){
        productDao?.delete(product)
    }

    fun clearProductDetail(id : Int){
        productDao?.clearDetail(id)
    }

}