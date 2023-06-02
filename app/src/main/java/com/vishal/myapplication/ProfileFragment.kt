package com.vishal.myapplication

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.internal.ViewUtils.dpToPx
import com.google.android.material.tabs.TabLayout
import com.vishal.myapplication.R


class ProfileFragment : Fragment() {

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




        return view
    }


}