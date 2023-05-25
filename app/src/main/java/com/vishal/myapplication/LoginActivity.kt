package com.vishal.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtLog = findViewById<TextView>(R.id.txt_switch2);

        txtLog.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}