package com.vishal.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Profile_Images_Fragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_profile_images, container, false)

         val sampleImages = arrayOf(
            R.drawable.sample_post_image1,
            R.drawable.sample_post_image2,
            R.drawable.sample_post_image3,
            R.drawable.sample_post_image4,
            R.drawable.sample_post_image5,
            R.drawable.sample_post_image6,
            R.drawable.sample_post_image7,
            R.drawable.sample_image_post8,
            R.drawable.sample_image_post9
        )

        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view_profile_images)
    //    val gridLayoutManager= GridLayoutManager(requireContext(),3) // 3 columns
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = linearLayoutManager

        val imageAdapter = ProfilePostImageAdapter(sampleImages.toList())
        recyclerView.adapter = imageAdapter

        return view
    }

}