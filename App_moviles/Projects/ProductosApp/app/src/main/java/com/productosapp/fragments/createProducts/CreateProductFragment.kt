package com.productosapp.fragments.createProducts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.database.AppDataBase
import com.productosapp.database.ImageDao
import com.productosapp.database.ProductsDao
import com.productosapp.database.UserDao
import com.productosapp.entities.Products

class CreateProductFragment : Fragment() {

    private var db: AppDataBase? = null
    private var productDao: ProductsDao? = null
    private var imageDao  : ImageDao? = null
    private var userDao   : UserDao? = null

    lateinit var v: View
    lateinit var btnLoadImage   : Button
    lateinit var btnMakeProduct : Button

    lateinit var newProduct             :Products
    lateinit var inputItemproduct       :TextView
    lateinit var inputBrandproduct      :TextView
    lateinit var inputModelProdut       :TextView
    lateinit var inputCostPricePoduct   :TextView
    lateinit var inputSellingPricePoduct:TextView
    lateinit var productImage:           ImageView
    lateinit var url : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        v = inflater.inflate(R.layout.fragment_create_product, container, false)

        btnLoadImage            = v.findViewById(R.id.btnLoadImage)
        btnMakeProduct          = v.findViewById(R.id.btnMakeProduct)
        inputItemproduct        = v.findViewById(R.id.inputItemproduct)
        inputBrandproduct       = v.findViewById(R.id.inputBrandproduct)
        inputModelProdut        = v.findViewById(R.id.inputModelProdut)
        inputCostPricePoduct    = v.findViewById(R.id.inputCostPricePoduct)
        inputSellingPricePoduct = v.findViewById(R.id.inputSellingPricePoduct)
        productImage            = v.findViewById(R.id.productImage)

        return v
    }

    override fun onStart() {
        super.onStart()
        db = AppDataBase.getInstance(requireContext())
        productDao = db?.ProductsDao()
        productDao?.loadAllProducts()

        imageDao = db?.ImageDao()
        imageDao?.loadAllImages()

        userDao =db?.UserDao()
        userDao?.loadAllUsers()

        btnLoadImage.setOnClickListener {
            if(inputItemproduct.text.isEmpty())
            {
                Snackbar.make(v, "Ingrese un item válido...", Snackbar.LENGTH_SHORT).show()
            }
            else
            {
                val imageUrl = inputItemproduct.text.toString()

                if(imageUrl == "termo"){
                    url =
                        "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F0ea0a38f-2efa-467b-bc04-181322345d61%2F004.jpg?id=fbe09711-ba23-4e7d-9c50-949dec3fd0a6&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                }
                if(imageUrl == "pava")
                {
                    url =
                        "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F1ae4fd3a-ce7f-4146-9d9c-7c413adc8f06%2F003.jpg?id=28e27ca3-cffd-4422-b099-ba33b812610e&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                }
                if(imageUrl == "horno")
                {
                    url =
                        "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F790ff253-500c-4597-8ba3-0aacbb0abe62%2F001.jpg?id=2ee578f8-124c-4b87-99bb-403690e7c63c&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                }
                if(imageUrl == "anafe")
                {
                    url =
                        "https://www.notion.so/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2Ffcdd389f-4fa6-4aae-b94a-06645c646e51%2F003.jpg?id=9a13b4c9-8194-48c9-9105-cf971f37bf7a&table=block&spaceId=80386716-11d9-4f38-abed-d26d1506ba10&width=600&userId=02d6dbcf-c7d3-41ab-846e-77a07e8b5c83&cache=v2"
                }

                Glide.with(this)
                    .load(url)
                    .into(productImage)
            }
        }

        btnMakeProduct.setOnClickListener {

            if(inputItemproduct.text.isEmpty())
            {
                Snackbar.make(v, "Ingrese un item", Snackbar.LENGTH_SHORT).show()
            }
            if(inputBrandproduct.text.isEmpty())
            {
                Snackbar.make(v, "Ingrese una marca", Snackbar.LENGTH_SHORT).show()
            }
            if(inputModelProdut.text.isEmpty())
            {
                Snackbar.make(v, "Ingrese un modelo", Snackbar.LENGTH_SHORT).show()
            }
            if(inputCostPricePoduct.text.isEmpty())
            {
                Snackbar.make(v, "Ingrese un precio de costo", Snackbar.LENGTH_SHORT).show()
            }
            if(inputSellingPricePoduct.text.isEmpty())
            {
                Snackbar.make(v, "Ingrese un precio de venta", Snackbar.LENGTH_SHORT).show()
            }
            else{
                var user= userDao?.findUserLogged()
                newProduct= makeNewProduct(user!!.id)
                productDao?.insertProduct(newProduct)
                Snackbar.make(v, "Producto agregado a la lista", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

        }
    }

    private fun makeNewProduct(userid : Int): Products {

        val newId                 = userid
        val newItemproduct        = inputItemproduct.text
        val newDetail             = 0
        val newBrandproduct       = inputBrandproduct.text
        val newModelProdut        = inputModelProdut.text
        val newCostPricePoduct    = inputCostPricePoduct.text
        val newSellingPricePoduct = inputSellingPricePoduct.text
        val newImageUri           = url

        return Products(
                        newId,
                        newItemproduct.toString(),
                        newDetail,
                        newBrandproduct.toString(),
                        newModelProdut.toString(),
                        newCostPricePoduct.toString().toInt(),
                        newSellingPricePoduct.toString().toInt(),
                        newImageUri
        )
    }
}
