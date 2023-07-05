package com.productosapp.fragments.createProducts

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.lifecycle.Observer
import androidx.lifecycle.VIEW_MODEL_STORE_OWNER_KEY
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.collection.LLRBNode
import com.productosapp.R
import com.productosapp.entities.Products
import kotlin.properties.Delegates

class CreateProductFragment : Fragment() {

    lateinit var v: View

    lateinit var btnLoadImage: Button
    lateinit var btnMakeProduct: Button

    lateinit var inputItemproduct: TextView
    lateinit var inputBrandproduct: TextView
    lateinit var inputModelProdut: TextView
    lateinit var inputCostPricePoduct: TextView
    lateinit var inputSellingPricePoduct: TextView
    var someEmpty by Delegates.notNull<Boolean>()

    lateinit var productImage: ImageView
    lateinit var url: String

    var product: Products = Products()

    private lateinit var viewModel: CreateProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_create_product, container, false)
        loadFindByView()
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CreateProductViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()
        btnLoadImage.setOnClickListener {
            IsSomeInputEmpty()
            if (!someEmpty) {
                getInput()
                viewModel.getUriImageProduct()
                viewModel.uri.observe(viewLifecycleOwner, Observer { result ->
                    Glide.with(this)
                        .load(result)
                        .into(productImage)})
            } else {
                Toast.makeText(requireContext(), "Complete los campos obligatorios", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        btnMakeProduct.setOnClickListener {
            IsSomeInputEmpty()
            if (!someEmpty) {
                getInput()
                viewModel.createNewProduct()
                viewModel.viewState.observe(viewLifecycleOwner){
                    when(it){
                        CreateProductViewModel.STATE_DONE->{
                            Toast.makeText(requireContext(), "Producto agregado a la lista", Toast.LENGTH_SHORT).show()
                            findNavController().popBackStack()
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "Complete los campos obligatorios", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun getInput() {
        product.item = inputItemproduct.text.toString()
        product.brand = inputBrandproduct.text.toString()
        product.model = inputModelProdut.text.toString()

        if (inputCostPricePoduct.text.isNullOrEmpty()) {
            product.costprice = 0
        } else {
            product.costprice = inputCostPricePoduct.text.toString().toInt()
        }
        if (inputSellingPricePoduct.text.isNullOrEmpty()) {
            product.costprice = 0
        } else {
            product.sellingprice = inputSellingPricePoduct.text.toString().toInt()
        }
        viewModel.productDb.value = product
    }

    private fun IsSomeInputEmpty() {
        val color = Color.rgb(255, 230, 230)

        if (inputItemproduct.text.isNullOrEmpty()) {
            someEmpty = true
            inputItemproduct.setBackgroundColor(color)
        } else {
            inputItemproduct.setBackgroundResource(android.R.color.transparent)
            someEmpty = false
        }
        if (inputBrandproduct.text.isNullOrEmpty()) {
            someEmpty = true
            inputBrandproduct.setBackgroundColor(color)
        } else {
            inputBrandproduct.setBackgroundResource(android.R.color.transparent)
            someEmpty = false
        }
        if (inputModelProdut.text.isNullOrEmpty()) {
            someEmpty = true
            inputModelProdut.setBackgroundColor(color)
        } else {
            inputModelProdut.setBackgroundResource(android.R.color.transparent)
            someEmpty = false
        }
        if (inputCostPricePoduct.text.isNullOrEmpty()) {
            someEmpty = true
            inputCostPricePoduct.setBackgroundColor(color)
        } else {
            inputCostPricePoduct.setBackgroundResource(android.R.color.transparent)
            someEmpty = false
        }
        if (inputSellingPricePoduct.text.isNullOrEmpty()) {
            someEmpty = true
            inputSellingPricePoduct.setBackgroundColor(color)
        } else {
            inputSellingPricePoduct.setBackgroundResource(android.R.color.transparent)
            someEmpty = false
        }
    }

    private fun loadFindByView() {
        btnLoadImage = v.findViewById(R.id.btnLoadImage)
        btnMakeProduct = v.findViewById(R.id.btnMakeProduct)
        inputItemproduct = v.findViewById(R.id.inputItemproduct)
        inputBrandproduct = v.findViewById(R.id.inputBrandproduct)
        inputModelProdut = v.findViewById(R.id.inputModelProdut)
        inputCostPricePoduct = v.findViewById(R.id.inputCostPricePoduct)
        inputSellingPricePoduct = v.findViewById(R.id.inputSellingPricePoduct)
        productImage = v.findViewById(R.id.productImage)
    }
}
