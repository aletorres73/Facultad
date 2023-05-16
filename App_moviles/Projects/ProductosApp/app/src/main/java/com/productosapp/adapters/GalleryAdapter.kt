package com.productosapp.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.productosapp.R
import com.productosapp.entities.Image

class GalleryAdapter(
    var images: MutableList<Image>,
):RecyclerView.Adapter<GalleryAdapter.GalleryHolder>() {

    class GalleryHolder(v: View) : RecyclerView.ViewHolder(v) {
        var view: View
        init {
            this.view = v
        }
        val imageProduct: ImageView = view.findViewById(R.id.imageProduct)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image,parent,false)
        return GalleryHolder(view)
    }

    override fun getItemCount(): Int = images.size

    override fun onBindViewHolder(holder: GalleryHolder, position: Int) {
        val image = images[position]
        Glide.with(holder.itemView.context)
            .load(image.uri)
            .centerCrop()
            .into(holder.imageProduct)

    }
    fun addImage(image: Image) {
        images.add(image)
        notifyItemInserted(images.size - 1)
        notifyDataSetChanged()
    }

}
