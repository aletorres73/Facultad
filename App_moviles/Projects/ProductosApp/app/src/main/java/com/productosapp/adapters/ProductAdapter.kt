package com.productosapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.productosapp.R
import com.productosapp.entities.Products

class ProductAdapter(
    private var productlist: MutableList<Products?>?,
    var onClick: (Int) -> Unit
):RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    private val VIEW_TYPE_PRODUCT = 0
    private val VIEW_TYPE_CREATE_PRODUCT = 1


    class ProductHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view: View

        init {
            this.view = v
        }

        fun setItem(item: String) {
            val txtItem: TextView = view.findViewById(R.id.txtItem)
            txtItem.text = item
        }

        fun setCostPrice(costprice: Int) {
            val txtPriceCost: TextView = view.findViewById(R.id.txtPriceCost)
            txtPriceCost.text = costprice.toString()
        }

        fun setSellPrice(sellingprice: Int) {
            val txtSellingPrice: TextView = view.findViewById(R.id.txtSellingPrice)
            txtSellingPrice.text = sellingprice.toString()
        }

        fun setBrand(brand: String) {
            val txtBrand: TextView = view.findViewById(R.id.txtBrand)
            txtBrand.text = brand
        }

        fun setModel(model: String) {
            val txtModel: TextView = view.findViewById(R.id.txtModel)
            txtModel.text = model
        }
        fun setImage(image: String)
        {
            val imageProductItem: ImageView = view.findViewById(R.id.imageProductItem)
            Glide.with(imageProductItem.context)
                .load(image)
                .into(imageProductItem)
        }

        fun getCard(): CardView {
            return view.findViewById(R.id.cardImage)
        }
        fun getNewItem():TextView{
            val createNewProductText: TextView = view.findViewById(R.id.txtAddProduct)
            createNewProductText.text = "Crear nuevo producto"
            return  createNewProductText
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == productlist?.size!! - 1) {
            VIEW_TYPE_CREATE_PRODUCT
        } else {
            VIEW_TYPE_PRODUCT
        }
    }

    override fun getItemCount(): Int = productlist?.size!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
//        val view: View = when (viewType) {
//            VIEW_TYPE_CREATE_PRODUCT -> {
//                layoutInflater.inflate(R.layout.item_create_product, parent, false)
//            }
//            else -> {
//                layoutInflater.inflate(R.layout.item_product, parent, false)
//            }
//        }
        val view = layoutInflater.inflate(R.layout.item_product, parent, false)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
<<<<<<< HEAD
<<<<<<< HEAD
        var id =
=======
>>>>>>> origin/product2
=======
        var id =
>>>>>>> origin/product2.2
        if (position < (itemCount + 1)) {
            holder.setItem(productlist?.get(position)?.item!!)
            holder.setBrand(productlist?.get(position)?.brand!!)
            holder.setModel(productlist?.get(position)?.model!!)
            holder.setCostPrice(productlist?.get(position)?.costprice!!)
            holder.setSellPrice(productlist?.get(position)?.sellingprice!!)
            holder.setImage(productlist?.get(position)?.imageuri!!)
            holder.getCard().setOnClickListener {
                //lo que se quiera hacer cuando se hace click
                onClick(position) // ejecuta la función onClick que se la pasa una position
            }
        } else {
            //holder.getNewItem()
            holder.getCard().setOnClickListener {
                //lo que se quiera hacer cuando se hace click
                onClick(position) // ejecuta la función onClick que se la pasa una posi
            }
        }
    }
}
