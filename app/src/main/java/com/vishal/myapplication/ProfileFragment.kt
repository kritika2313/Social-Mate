package com.vishal.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment : Fragment() {
    private val TAG= "ProfileFragment";
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val tabLayout: TabLayout = view.findViewById(R.id.profile_tab_layout)
        val viewPager: ViewPager = view.findViewById(R.id.profileViewPager)

        val adapter  = ViewPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter

        adapter.addFragment(Profile_Images_Fragment(),"Images")
        adapter.addFragment(Profile_Videos_Fragment(),"Videos")

        adapter.notifyDataSetChanged()
        tabLayout.setupWithViewPager(viewPager)

        val name: TextView = view.findViewById(R.id.userName);
        val imageUrl: CircleImageView= view.findViewById(R.id.cir_pro_img)
        val about : TextView =view.findViewById(R.id.headline_txt)
        val db=Firebase.firestore;
        val docRef= db.collection("users").document("vikash123@gmail_com");
        docRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result;
                val user = document?.get("username") as String;
                val userImg = document.get("imageUrl") as String;
                val profession=document.get("profession") as String
                val headline =document.get("headline") as String
                name.text=user;
                about.text=String.format("%s | %s", profession, headline)
                Picasso.get().load(userImg).into(imageUrl)
                Log.d(TAG, "Value of image $userImg")
                Log.d(TAG, "Value of $user")
            }
        }
        return view
    }


}