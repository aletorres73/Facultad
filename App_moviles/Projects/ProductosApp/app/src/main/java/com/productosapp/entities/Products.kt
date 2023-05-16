package com.productosapp.entities

class ProductManager {
    val products = mutableListOf<Products>()

    init {
        products.addAll(listOf(
//            Products(item = "Termo", brand = "Kanson" , model = "1.3L", costprice = 6400, sellingprice = 8400),
//            Products(item = "Termo", brand = "Kanson" , model = "1L"  , costprice = 6000, sellingprice = 8000),
//            Products(item = "Termo", brand = "Peabody", model = "Eléctrico", costprice = 22100, sellingprice = 25100),
//            Products(item = "Horno", brand = "Axel" , model = "Standard", costprice = 19400, sellingprice = 22400),
//            Products(item = "Termo", brand = "Kanson" , model = "1.3L", costprice = 6400, sellingprice = 8400),
//            Products(item = "Termo", brand = "Kanson" , model = "1L"  , costprice = 6000, sellingprice = 8000),
//            Products(item = "Termo", brand = "Peabody", model = "Eléctrico", costprice = 22100, sellingprice = 25100),
//            Products(item = "Horno", brand = "Axel" , model = "Standard", costprice = 19400, sellingprice = 22400),
            Products(item = "", brand = "" , model = "", costprice = 0, sellingprice = 0)

        ).sortedWith(compareBy({ it.item.isEmpty() }, { it.item })))
    }

    fun getProductList(): MutableList<Products> {
        return products }

    fun addProduct(product: Products) {
        products.add(product) }

    fun removeProducts(product: Products) {
        products.remove(product) }
}

class Products(
    var item:           String,
    var brand:          String,
    var model:          String,
    var costprice:      Int,
    var sellingprice:   Int,
    )