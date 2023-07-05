package com.productosapp.fragments.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.*
import com.productosapp.entities.Products
import com.productosapp.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
class HomeViewModel : ViewModel() {

    var productUserListDb: MutableLiveData<MutableList<Products>> = MutableLiveData()
    var viewState: MutableLiveData<String> = MutableLiveData()


    private val userSource: FirebaseDataUserSource by inject(FirebaseDataUserSource::class.java)
    private val productSource: FirebaseDataProductSource by inject(FirebaseDataProductSource::class.java)

    fun init(){
        viewState.value = STATE_INIT
    }
    fun getList(){
        viewModelScope.launch {
            userSource.userFb?.let { productSource.loadProductByUserId(it.id) }
            if(productSource.productListFb.isNullOrEmpty()){
                viewState.value = STATE_EMPTY
            }else{
                viewState.value = STATE_LOADING
            }
        }
    }
    fun loadList() {
        productUserListDb.value = productSource.productListFb
        viewState.value = STATE_DONE
    }
    fun refresh(){
        viewState.value = STATE_INIT
    }
    fun setDetailProduct(productForDetailView: Products) {
        productSource.productFb = productForDetailView
    }

    companion object {
        const val STATE_ERROR   = "error"
        const val STATE_DONE    = "done"
        const val STATE_LOADING = "loading"
        const val STATE_EMPTY   = "empty"
        const val STATE_INIT    = "init"
    }
}
