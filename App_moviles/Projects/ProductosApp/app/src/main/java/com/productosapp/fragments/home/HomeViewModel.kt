package com.productosapp.fragments.home

import android.content.Context
import androidx.lifecycle.ViewModel
import com.productosapp.database.AppDataBase
import com.productosapp.database.ProductsDao
import com.productosapp.database.UserDao
import com.productosapp.entities.Products
import com.productosapp.entities.User

class HomeViewModel : ViewModel() {

    private var db: AppDataBase? = null
    private var productDao: ProductsDao? = null
    private var userDao: UserDao? = null

    fun instanceDataBase(context: Context){
        db = AppDataBase.getInstance(context)
        productDao = db?.ProductsDao()
        productDao?.loadAllProducts()
        userDao = db?.UserDao()
        userDao?.loadAllUsers()
    }

    fun getuserProduct(): User?{
        return  userDao?.findUserLogged()
    }

    fun getListProduct(user: User): MutableList<Products?>? {
        return productDao?.loadProductById(user!!.id)
    }

    fun setDetailProduct(product: Products){
        productDao?.setDetail(product.id)
    }
}