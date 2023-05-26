package com.vishal.socialmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.core.os.postDelayed
import com.google.firebase.auth.FirebaseAuth
import com.vishal.myapplication.LoginActivity
import com.vishal.myapplication.R
import com.vishal.myapplication.SignUpActivity

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val btn = findViewById<Button>(R.id.signOutBtn)

        btn.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
