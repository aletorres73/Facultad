package com.productosapp.fragments.createProducts

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
    val uri: MutableLiveData<String> = MutableLiveData()
    val viewState: MutableLiveData<String> = MutableLiveData()

    companion object{
        const val STATE_DONE = "done"
    }

    fun getUriImageProduct() {
        when (productDb.value?.item) {
            "termo" -> {
                productDb.value!!.imageuri =
                    "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F0ea0a38f-2efa-467b-bc04-181322345d61%2F004.jpg?id=fbe09711-ba23-4e7d-9c50-949dec3fd0a6&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                uri.value = productDb.value!!.imageuri
            }
            "pava" -> {
                productDb.value!!.imageuri =
                    "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1ae4fd3a-ce7f-4146-9d9c-7c413adc8f06%2F003.jpg?id=28e27ca3-cffd-4422-b099-ba33b812610e&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                uri.value = productDb.value!!.imageuri
            }
            "horno" -> {
                productDb.value!!.imageuri =
                    "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F790ff253-500c-4597-8ba3-0aacbb0abe62%2F001.jpg?id=2ee578f8-124c-4b87-99bb-403690e7c63c&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                uri.value = productDb.value!!.imageuri
            }
            "anafe" -> {
                productDb.value!!.imageuri =
                    "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ffcdd389f-4fa6-4aae-b94a-06645c646e51%2F003.jpg?id=9a13b4c9-8194-48c9-9105-cf971f37bf7a&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                uri.value = productDb.value!!.imageuri
            }
        }
    }

    fun createNewProduct() {
        viewModelScope.launch() {
            val userid = userSource.userFb!!.id
            val id = productSource.getProductId()
//            val id = if(productSource.productListFb.size == 0){
//                    0
//                } else{
//                    productSource.productListFb.last().id +1
//                }
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
                    it.imageuri )
                productSource.insertProduct(newProduct)
                viewState.value = STATE_DONE
            }
        }
    }
}

