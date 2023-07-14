package com.productosapp.fragments.home

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
import com.productosapp.database.AppDataBase
import com.productosapp.database.ProductsDao
import com.productosapp.database.UserDao

class HomeFragment : Fragment() {

    private var db: AppDataBase? = null
    private var productDao: ProductsDao? = null
    private var userDao: UserDao? = null

    lateinit var v          : View
    lateinit var recProduct : RecyclerView
    lateinit var adapter    : ProductAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_home, container, false)

        recProduct = v.findViewById(R.id.recProduct)
        return v
    }

    override fun onStart() {
        super.onStart()

        db = AppDataBase.getInstance(requireContext())
        productDao = db?.ProductsDao()
        productDao?.loadAllProducts()
        userDao = db?.UserDao()
        userDao?.loadAllUsers()

        // Obtener la lista de productos
        val user = userDao?.findUserLogged()
        val productList = productDao?.loadProductById(user!!.id)

        // Configurar el adaptador de la lista
        adapter = ProductAdapter(productList) { position ->
            if (position !=  adapter.itemCount + 1) {

                productDao?.setDetail(productList?.get(position)?.id!!)

                val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                findNavController().navigate(action)
            }
        }

        // Configurar el RecyclerView
        recProduct.layoutManager = LinearLayoutManager(context)
        recProduct.adapter = adapter
    }


}