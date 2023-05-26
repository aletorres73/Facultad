package com.productosapp.database

import androidx.room.Dao
import androidx.room.*
import com.productosapp.entities.Products
import com.productosapp.entities.User


@Dao
interface ProductsDao{
    @Query("SELECT * FROM products ORDER BY id")
    fun loadAllProducts(): MutableList<Products?>?

    @Insert
    fun insertProduct(user: Products?)

    @Update
    fun update(user: Products)

    @Delete
    fun delete(user: Products?)

    @Query("SELECT * FROM products WHERE id = :id")
    fun loadProductById(id: Int):Products?

    @Query("SELECT * FROM products WHERE brand = :brand")
    fun loadUserByBrand(brand: String):Products?

    @Query("UPDATE products SET detail = 1 WHERE id = :productId")
    fun setDetail(productId: Int)

    @Query("UPDATE products SET detail = 0 WHERE id = :productId")
    fun clearDetail(productId: Int)

    @Query("SELECT * FROM products WHERE detail = 1")
    fun findProductDetail(): Products?
}





