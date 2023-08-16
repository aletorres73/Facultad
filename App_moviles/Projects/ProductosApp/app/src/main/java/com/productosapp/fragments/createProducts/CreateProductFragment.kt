package com.productosapp.fragments.createProducts

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build.*
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.entities.Products
import kotlin.properties.Delegates


class CreateProductFragment : Fragment() {

    private val REQUEST_GALLERY = 1001
    private val REQUEST_CAMERA = 1002

    lateinit var v: View

    lateinit var btnCamera: Button
    lateinit var btnGallery: Button
    lateinit var btnMakeProduct: Button
    //lateinit var btnDeleteImage: Button

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
    var foto : Uri? = null

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

        btnGallery.setOnClickListener() {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val filePermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(filePermission, REQUEST_GALLERY)
            } else {
                openGallery()
            }
        }


        btnCamera.setOnClickListener {
            if (checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                ||  checkSelfPermission(requireContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val filePermission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(filePermission, REQUEST_CAMERA)
            } else {
                openCamera()
            }
        }
        btnMakeProduct.setOnClickListener {
            IsSomeInputEmpty()
            if (!someEmpty) {
                getInput()
                createNewProduct()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Complete los campos obligatorios",
                    Toast.LENGTH_SHORT).show()
            }
        }
//        btnDeleteImage.setOnClickListener{
//            viewModel.deleteImage()
//        }
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
        btnCamera = v.findViewById(R.id.btnCamera)
        btnGallery = v.findViewById(R.id.btnGallery)
        btnMakeProduct = v.findViewById(R.id.btnMakeProduct)
        //btnDeleteImage = v.findViewById(R.id.btnDeleteImage)
        inputItemproduct = v.findViewById(R.id.inputItemproduct)
        inputBrandproduct = v.findViewById(R.id.inputBrandproduct)
        inputModelProdut = v.findViewById(R.id.inputModelProdut)
        inputCostPricePoduct = v.findViewById(R.id.inputCostPricePoduct)
        inputSellingPricePoduct = v.findViewById(R.id.inputSellingPricePoduct)
        productImage = v.findViewById(R.id.productImage)
    }

    private fun createNewProduct() {
        viewModel.createNewProduct()
        viewModel.viewState.observe(viewLifecycleOwner) {
            when (it) {
                CreateProductViewModel.STATE_DONE -> {
                    Toast.makeText(
                        requireContext(),
                        "Producto agregado a la lista",
                        Toast.LENGTH_SHORT
                    ).show()
                    findNavController().popBackStack()
                }
            }
        }
    }

    private fun openGallery() {
        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = "image/*"
        startActivityForResult(intentGallery, REQUEST_GALLERY)
    }

    private fun openCamera(){
        val value = ContentValues()
        value.put(MediaStore.Images.Media.TITLE, "New Image")
        foto= requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value)
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, foto)
        startActivityForResult(cameraIntent,REQUEST_CAMERA)
    }

    private fun getImageGallery(data: Intent?) {
        val imageUri = data?.data
        if (imageUri != null) {
            productImage.setImageURI(imageUri)
            viewModel.uri.value = imageUri
            viewModel.uploadImage()
        }
    }

    private fun getImageCamera(data: Uri?){
        if (data != null) {
            productImage.setImageURI(data)
            viewModel.uri.value = data
            viewModel.uploadImage()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY) {
            getImageGallery(data)
        }
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CAMERA) {
            getImageCamera(foto)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_GALLERY->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openGallery()
                else
                    Toast.makeText(requireContext(),"No tienes permiso a la galería",Toast.LENGTH_SHORT).show()
            }
            REQUEST_CAMERA->{
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    openCamera()
                else
                    Toast.makeText(requireContext(),"No tienes permiso a la cámara",Toast.LENGTH_SHORT).show()
               }
            }
        }
}






