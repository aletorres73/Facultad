package com.productosapp.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.adapters.ProductAdapter
import com.productosapp.entities.ProductManager
import com.productosapp.entities.Products

class HomeFragment : Fragment() {

    lateinit var v          : View
    lateinit var recProduct : RecyclerView
    lateinit var adapter    : ProductAdapter
    var productRepository   : ProductManager = ProductManager()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        recProduct = v.findViewById(R.id.recProduct)
        return v
    }

    override fun onStart() {
        super.onStart()

        // Obtener la lista de productos
        val productList = productRepository.getProductList()


        // Configurar el adaptador de la lista
        adapter = ProductAdapter(productList) { position ->
            if (position ==  adapter.itemCount - 1) {
                val action = HomeFragmentDirections.actionHomeFragmentToCreateProductFragment22()
                findNavController().navigate(action)

            } else {
                // Si se selecciona un producto existente, mostrar un Snackbar con su información
                Snackbar.make(v, "click en ${productRepository.products[position].item}: " +
                        productRepository.products[position].model, Snackbar.LENGTH_LONG).show()
            }
        }

        // Configurar el RecyclerView
        recProduct.layoutManager = LinearLayoutManager(context)
        recProduct.adapter = adapter
    }


}