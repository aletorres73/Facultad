package com.productosapp.fragments.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.entities.Products
import com.productosapp.fra.DetailViewModel

class DetailFragment : Fragment() {

    lateinit var v: View
    lateinit var valItemProduct  : TextView
    lateinit var valBrandProduct : TextView
    lateinit var vaModelPoduct   : TextView
    lateinit var valCostPrice    : TextView
    lateinit var valSellingPrice : TextView
    lateinit var imageDetail     : ImageView
    lateinit var btnRemoveProduct: TextView

    private lateinit var viewModel : DetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v= inflater.inflate(R.layout.fragment_detail, container, false)

        valItemProduct  = v.findViewById(R.id.valItemProduct)
        valBrandProduct = v.findViewById(R.id.valBrandProduct)
        vaModelPoduct   = v.findViewById(R.id.vaModelPoduct)
        valCostPrice    = v.findViewById(R.id.valCostPrice)
        valSellingPrice = v.findViewById(R.id.valSellingPrice)
        imageDetail     = v.findViewById(R.id.imageDetail)
        btnRemoveProduct= v.findViewById(R.id.btnRemoveProduct)

        return v
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(DetailViewModel::class.java)
    }
    override fun onStart() {
        super.onStart()
        val productDetail = viewModel.getProductDetail()

        if(productDetail != null){
            loadProductView(productDetail)
            viewModel.getProductImageUri()

            viewModel.image.observe(viewLifecycleOwner, Observer {result ->
                Glide.with(this)
                    .load(result)
                    .into(imageDetail)
            } )
            viewModel.clearProductDetail(productDetail.id)
        }
        btnRemoveProduct.setOnClickListener{
            viewModel.removeProduct(productDetail)

            Snackbar.make(v, "Producto eliminado...", Snackbar.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

    }
    private fun loadProductView(productDetail: Products){
        valItemProduct.text  = productDetail.item
        valBrandProduct.text = productDetail.brand
        vaModelPoduct.text   = productDetail.model
        valCostPrice.text    = productDetail.costprice.toString()
        valSellingPrice.text = productDetail.sellingprice.toString()
    }
//falta crear una vista que edite el producto

}