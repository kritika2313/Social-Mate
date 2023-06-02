package com.vishal.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class ProfilePostImageAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<ProfilePostImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_post_item_image,parent,false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResId = imageList[position]
        holder.imageView.setImageResource(imageResId)
    }

    override fun getItemCount(): Int {
        return imageList.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.image_item)
    }

}