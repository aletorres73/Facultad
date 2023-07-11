package com.productosapp.fragments.createProducts


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.entities.Products
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class CreateProductViewModel : ViewModel() {

    private val userSource: FirebaseDataUserSource by inject(
        FirebaseDataUserSource::class.java
    )
    private val productSource: FirebaseDataProductSource by inject(
        FirebaseDataProductSource::class.java
    )

    var productDb: MutableLiveData<Products> = MutableLiveData()
    val uri: MutableLiveData<Uri> = MutableLiveData()
    val viewState: MutableLiveData<String> = MutableLiveData()

    companion object {
        const val STATE_DONE = "done"
    }

    fun uploadImage() {
        viewModelScope.launch {
            uri.value?.let { uri ->
                productSource.uploadImage(uri)
            }
        }
    }

    fun createNewProduct() {
        viewModelScope.launch() {
            val userid = userSource.currentUID
            val id = productSource.getProductId()

            productDb.value?.let {
                val newProduct = Products(
                    id,
                    userid,
                    it.item,
                    0,
                    it.brand,
                    it.model,
                    it.costprice,
                    it.sellingprice,
                    it.imageuri
                )
                productSource.insertProduct(newProduct)
                viewState.value = STATE_DONE
            }
        }
    }
}