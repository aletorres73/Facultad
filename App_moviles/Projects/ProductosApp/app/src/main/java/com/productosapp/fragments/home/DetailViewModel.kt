package com.productosapp.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.entities.Products
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class DetailViewModel : ViewModel() {

    private val productSource : FirebaseDataProductSource by inject(FirebaseDataProductSource::class.java)

    val productDb    : MutableLiveData<Products> = MutableLiveData()
    val image        : MutableLiveData<String>    = MutableLiveData()
    val viewState    : MutableLiveData<String>   = MutableLiveData()

    companion object{
        const val STATE_DONE    = "done"
        const val STATE_REMOVING= "removing"
        const val STATE_LAST = "last"
    }

    fun getProductDetail() {
        productDb.value = productSource.productFb
    }
    fun getProductImageUri() {
        image.value = productDb.value?.imageuri
    }
    fun removeProduct(id : Int){
        viewModelScope.launch(){
            productSource.delete(id)
            viewState.value = STATE_DONE
        }
    }
    fun emptyList(){
        if(productSource.productListFb.size == 0){
            viewState.value = STATE_LAST
        }
        else{
            viewState.value = STATE_REMOVING
        }

    }
}