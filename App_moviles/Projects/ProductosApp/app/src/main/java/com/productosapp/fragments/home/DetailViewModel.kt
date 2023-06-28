package com.productosapp.fra


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.entities.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class DetailViewModel : ViewModel() {

    private val productSource : FirebaseDataProductSource by inject(FirebaseDataProductSource::class.java)

    val productDb : MutableLiveData<Products> = MutableLiveData()
    val image     :MutableLiveData<String>    = MutableLiveData()

    fun getProductDetail(): Products? {
        viewModelScope.launch(Dispatchers.Main) {
            productDb.value= productSource.findProductDetail()!!
        }
        return productDb.value
    }
    fun getProductImageUri() {
        image.value = productDb.value?.imageuri
    }

    fun removeProduct(product: Products?){
        viewModelScope.launch(Dispatchers.Main){
            productSource.delete(product)
        }
    }
    fun clearProductDetail(id : Int){
        viewModelScope.launch(Dispatchers.Main){
            productSource.clearDetail(id)
        }
    }

}