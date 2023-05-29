package com.vishal.socialmate

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.vishal.myapplication.*

class MainActivity : AppCompatActivity() {

    private lateinit var navView: BottomNavigationView

    private var isHomeFragmentVisible = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val addBtn = findViewById<FloatingActionButton>(R.id.fab)
        addBtn.setOnClickListener {
            replaceFragment(AddFragment(),false)
            navView.menu.setGroupCheckable(0,false,true)
        }

        replaceFragment(HomeFragment(),true)

        navView = findViewById(R.id.bottom_nav_view)

        navView.setOnNavigationItemSelectedListener {
            navView.menu.setGroupCheckable(0,true,true)
            when(it.itemId){
                R.id.home_frag -> replaceFragment(HomeFragment(),false)
                R.id.search_frag -> replaceFragment(SearchFragment(),false)
                R.id.ask_frag -> replaceFragment(AskFragment(),false)
                R.id.profile_frag -> replaceFragment(ProfileFragment(),false)
            }
            true
        }

        val placeHolderMenuItem = navView.menu.findItem(R.id.placeholder)
        placeHolderMenuItem.icon = ColorDrawable(Color.TRANSPARENT)
        placeHolderMenuItem.isEnabled = false
        placeHolderMenuItem.actionView?.setBackgroundResource(R.drawable.menu_item_placeholder_background)
    }

    override fun onBackPressed() {
        if(!isHomeFragmentVisible) {
            replaceFragment(HomeFragment(),false)
            navView.selectedItemId = R.id.home_frag
        } else {
            super.onBackPressed()
        }
    }

    private fun replaceFragment(fragment : Fragment,value : Boolean){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if(value)
        {
            fragmentTransaction.add(R.id.fragment_container,fragment)
        } else {
            fragmentTransaction.replace(R.id.fragment_container,fragment)
        }
        fragmentTransaction.commit()

        isHomeFragmentVisible = fragment is HomeFragment
    }
}
