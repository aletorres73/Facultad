package com.productosapp.fragments.createProducts

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.productosapp.R
import com.productosapp.adapters.GalleryAdapter
import com.productosapp.entities.Image
import com.productosapp.entities.ProductManager
import com.productosapp.entities.Products

class CreateProductFragment : Fragment() {

    lateinit var v: View
    lateinit var btnLoadImage   : Button
    lateinit var btnMakeProduct : Button

    lateinit var galleryAdapter : GalleryAdapter
    lateinit var recyclerGallery: RecyclerView

    private val imageList =              mutableListOf<Image>()
    var productRepository :              ProductManager = ProductManager()

    lateinit var newProduct             :Products
    lateinit var inputItemproduct       :TextView
    lateinit var inputBrandproduct      :TextView
    lateinit var inputModelProdut       :TextView
    lateinit var inputCostPricePoduct   :TextView
    lateinit var inputSellingPricePoduct:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_create_product, container, false)

        btnLoadImage     = v.findViewById(R.id.btnLoadImage)
        btnMakeProduct   = v.findViewById(R.id.btnMakeProduct)
        inputItemproduct = v.findViewById(R.id.inputItemproduct)
        inputBrandproduct = v.findViewById(R.id.inputBrandproduct)
        inputModelProdut = v.findViewById(R.id.inputModelProdut)
        inputCostPricePoduct = v.findViewById(R.id.inputCostPricePoduct)
        inputSellingPricePoduct = v.findViewById(R.id.inputSellingPricePoduct)

        recyclerGallery  = v.findViewById(R.id.recyclerGallery)

        return v
    }

    override fun onStart() {
        super.onStart()

        var productList = productRepository.getProductList()

        btnLoadImage.setOnClickListener {
            // Crear intent para seleccionar imagen
            val intent  = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE_LOAD_IMAGE)
        }

        btnMakeProduct.setOnClickListener {
            newProduct= makeNewProduct()
            productList.add(newProduct)
            Snackbar.make(v, "Producto agregado a la lista", Snackbar.LENGTH_SHORT).show()
        }

        // Configurar el adaptador de la lista
        galleryAdapter                = GalleryAdapter(imageList)//{
        recyclerGallery.adapter       = galleryAdapter
        recyclerGallery.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL
                                                                             , false)

    }

    private fun makeNewProduct(): Products {
        var newItemproduct        = inputItemproduct.text
        var newBrandproduct       = inputBrandproduct.text
        var newModelProdut        = inputModelProdut.text
        var newCostPricePoduct    = inputCostPricePoduct.text
        var newSellingPricePoduct = inputSellingPricePoduct.text

        return Products(newItemproduct.toString(),
                        newBrandproduct.toString(),
                        newModelProdut.toString(),
                        newCostPricePoduct.toString().toInt(),
                        newSellingPricePoduct.toString().toInt())

    }

    // Manejar resultado de la selección de imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            val imageUri  = data?.data

            if (imageUri != null) {
                val image = Image(imageUri)
                galleryAdapter.addImage(image)


            } else {
                Snackbar.make(v, "Error al cargar la imagen", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val REQUEST_CODE_LOAD_IMAGE = 1
    }
}
