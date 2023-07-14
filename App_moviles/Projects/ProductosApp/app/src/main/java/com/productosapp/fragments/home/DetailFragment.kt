package com.productosapp.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.database.AppDataBase
import com.productosapp.database.ProductsDao

class DetailFragment : Fragment() {

    private var db: AppDataBase? = null
    private var productDao: ProductsDao? = null

    lateinit var v: View
    lateinit var valItemProduct  : TextView
    lateinit var valBrandProduct : TextView
    lateinit var vaModelPoduct   : TextView
    lateinit var valCostPrice    : TextView
    lateinit var valSellingPrice : TextView
    lateinit var imageDetail     : ImageView
    lateinit var btnRemoveProduct : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v= inflater.inflate(R.layout.fragment_detail, container, false)
        valItemProduct  = v.findViewById(R.id.valItemProduct)
        valBrandProduct = v.findViewById(R.id.valBrandProduct)
        vaModelPoduct   = v.findViewById(R.id.vaModelPoduct)
        valCostPrice    = v.findViewById(R.id.valCostPrice)
        valSellingPrice = v.findViewById(R.id.valSellingPrice)
        imageDetail     = v.findViewById(R.id.imageDetail)
        btnRemoveProduct     = v.findViewById(R.id.btnRemoveProduct)


        return v
    }

    override fun onStart() {
        super.onStart()

        db = AppDataBase.getInstance(requireContext())
        productDao = db?.ProductsDao()
        productDao?.loadAllProducts()

        val productDetail = productDao?.findProductDetail()

        if(productDetail != null){

            valItemProduct.text  = productDetail?.item
            valBrandProduct.text = productDetail?.brand
            vaModelPoduct.text   = productDetail?.model
            valCostPrice.text    = productDetail?.costprice?.toInt().toString()
            valSellingPrice.text = productDetail?.sellingprice?.toInt().toString()

            Glide.with(this)
                .load(productDao?.getImageUrl())
                .into(imageDetail)
        }

        btnRemoveProduct.setOnClickListener{
            productDao?.delete(productDetail)
            Snackbar.make(v, "Producto eliminado...", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
    }
}