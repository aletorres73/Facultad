package com.productosapp.fragments.createProducts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.fragments.login_register.LoginFragment

class CreateProductFragment : Fragment() {

    lateinit var v                      : View

    lateinit var btnLoadImage           : Button
    lateinit var btnMakeProduct         : Button

    lateinit var inputItemproduct       : TextView
    lateinit var inputBrandproduct      : TextView
    lateinit var inputModelProdut       : TextView
    lateinit var inputCostPricePoduct   : TextView
    lateinit var inputSellingPricePoduct: TextView

    lateinit var productImage           : ImageView
    lateinit var url                    : String

    private lateinit var viewModel: CreateProductViewModel

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(CreateProductViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        btnLoadImage.setOnClickListener {
            getInput()

            if (inputItemproduct.text.isEmpty()) {
                Snackbar.make(v, "Ingrese un item válido...", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.getUriImageProduct()
                viewModel.uri.observe(viewLifecycleOwner, Observer{result ->
                    Glide.with(this)
                        .load(result)
                        .into(productImage) })
            }
        }

        btnMakeProduct.setOnClickListener {
            getInput()

            if (inputItemproduct.text.isEmpty()) {
                Snackbar.make(v, "Ingrese un item", Snackbar.LENGTH_SHORT).show()
            }
            if (inputBrandproduct.text.isEmpty()) {
                Snackbar.make(v, "Ingrese una marca", Snackbar.LENGTH_SHORT).show()
            }
            if (inputModelProdut.text.isEmpty()) {
                Snackbar.make(v, "Ingrese un modelo", Snackbar.LENGTH_SHORT).show()
            }
            if (inputCostPricePoduct.text.isEmpty()) {
                Snackbar.make(v, "Ingrese un precio de costo", Snackbar.LENGTH_SHORT).show()
            }
            if (inputSellingPricePoduct.text.isEmpty()) {
                Snackbar.make(v, "Ingrese un precio de venta", Snackbar.LENGTH_SHORT).show()
            } else {
                viewModel.createNewProduct()

                Snackbar.make(v, "Producto agregado a la lista", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }

        }
    }
    private fun getInput()
    {
        viewModel.item.value          = inputItemproduct.text.toString()
        viewModel.brand.value         = inputBrandproduct.text.toString()
        viewModel.model.value         = inputModelProdut.text.toString()
        viewModel.costprice.value     = inputCostPricePoduct.text.toString()
        viewModel.sellingprice.value  = inputSellingPricePoduct.text.toString()
    }
}
