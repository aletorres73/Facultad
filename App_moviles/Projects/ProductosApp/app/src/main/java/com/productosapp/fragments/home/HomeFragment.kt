package com.productosapp.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.adapters.ProductAdapter

class HomeFragment : Fragment() {

    lateinit var v          : View
    lateinit var recProduct : RecyclerView
    lateinit var adapter    : ProductAdapter

    private lateinit var viewModel : HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        recProduct = v.findViewById(R.id.recProduct)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

    }

    override fun onStart() {
        super.onStart()

        val user = viewModel.getUserProduct()
        val productList = viewModel.getListProduct(user!!)

        // Configurar el adaptador de la lista
        adapter = ProductAdapter(productList) { position ->
            if (position !=  adapter.itemCount + 1) {

                if (productList != null) {
                    viewModel.setDetailProduct(productList[position]!!)
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                    findNavController().navigate(action)
                }
                else{
                    Snackbar.make(v, "Lista de productos nula...", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        // Configurar el RecyclerView
        recProduct.layoutManager = LinearLayoutManager(context)
        recProduct.adapter = adapter
    }


}


