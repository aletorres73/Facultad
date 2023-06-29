package com.productosapp.fragments.createProducts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.productosapp.database.FirebaseDataProductSource
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.entities.Products
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateProductViewModel(private val userSource: FirebaseDataUserSource, private val productSource: FirebaseDataProductSource) : ViewModel() {

    val item        : MutableLiveData<String> = MutableLiveData()
    val brand       : MutableLiveData<String> = MutableLiveData()
    val model       : MutableLiveData<String> = MutableLiveData()
    val costprice   : MutableLiveData<String> = MutableLiveData()
    val sellingprice: MutableLiveData<String> = MutableLiveData()
    val uri         : MutableLiveData<String> = MutableLiveData()

    fun getUriImageProduct(){
        when (item.value) {
            "termo" -> {
                uri.value ="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F0ea0a38f-2efa-467b-bc04-181322345d61%2F004.jpg?id=fbe09711-ba23-4e7d-9c50-949dec3fd0a6&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                //return uri.value.toString()
            }
            "pava" -> {
                uri.value ="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1ae4fd3a-ce7f-4146-9d9c-7c413adc8f06%2F003.jpg?id=28e27ca3-cffd-4422-b099-ba33b812610e&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
//                return uri.value.toString()
            }
            "horno" -> {
                uri.value ="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F790ff253-500c-4597-8ba3-0aacbb0abe62%2F001.jpg?id=2ee578f8-124c-4b87-99bb-403690e7c63c&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                //return uri.value.toString()
            }
            "anafe" -> {
                uri.value ="https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ffcdd389f-4fa6-4aae-b94a-06645c646e51%2F003.jpg?id=9a13b4c9-8194-48c9-9105-cf971f37bf7a&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                //return uri.value.toString()
            }
//            else -> {
//                return ""
//            }
        }
    }

    fun createNewProduct() {
        viewModelScope.launch(Dispatchers.Main) {

            val user = userSource.getLoggedUser()
            val id   =productSource.getProductId()

            val newProduct = Products(
                id,
                user!!.id,
                item.value.toString(),
                0,
                brand.value.toString(),
                model.value.toString(),
                costprice.value!!.toInt() ,
                sellingprice.value!!.toInt(),
                uri.value!!
            )
            productSource.insertProduct(newProduct)
        }

    }
}