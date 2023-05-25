package com.vishal.socialmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.vishal.myapplication.R
import com.vishal.myapplication.SignUpActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed(3000)
        {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
    }
}
