package com.productosapp.fragments.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.adapters.ProductAdapter

class HomeFragment : Fragment() {

    private lateinit var v                    : View
    private lateinit var recProduct           : RecyclerView
    private lateinit var adapter              : ProductAdapter
    private lateinit var swipeRefreshLayout   : SwipeRefreshLayout

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_home, container, false)
        recProduct = v.findViewById(R.id.recProduct)
        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout)
        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        viewModel.init()
//        setupRecyclerView()
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            swipeRefreshLayout.isRefreshing = false
        }

        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when (state) {
                HomeViewModel.STATE_EMPTY -> {
                    viewModel.loadList()
                    showEmptyListState()
                }
                HomeViewModel.STATE_LOADING -> {
                    viewModel.loadList()
                    showLoadingState()
                }
                HomeViewModel.STATE_DONE -> {
                    showDoneState()
                }
                HomeViewModel.STATE_ERROR -> {
                    showErrorState()
                }
                HomeViewModel.STATE_INIT->{
                    viewModel.getList()

                }
            }
        }
    }
    private fun setupRecyclerView() {
        adapter = ProductAdapter(mutableListOf()) { }
        recProduct.layoutManager = LinearLayoutManager(context)
        recProduct.adapter = adapter
    }

    private fun showEmptyListState() {
        Toast.makeText(requireContext(), "Lista vacía", Toast.LENGTH_SHORT).show()
    }

    private fun showLoadingState() {
        // Mostrar un estado de carga en el adapter
        Toast.makeText(requireContext(), "Cargando...", Toast.LENGTH_SHORT).show()
    }

    private fun showDoneState() {
        viewModel.productUserListDb.observe(viewLifecycleOwner){
            adapter = ProductAdapter(it) { position ->
                if (position < it!!.size) {
                    viewModel.setDetailProduct(it[position])
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Lista de productos nula...", Toast.LENGTH_SHORT).show()
                }
            }
            recProduct.layoutManager = LinearLayoutManager(context)
            recProduct.adapter = adapter
            adapter.notifyItemRemoved(it.size -1)
            Toast.makeText(requireContext(), "Lista actualizada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showErrorState() {
        Toast.makeText(requireContext(), "Error al cargar la lista...", Toast.LENGTH_SHORT).show()
    }
}