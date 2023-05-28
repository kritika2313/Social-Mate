package com.vishal.socialmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vishal.myapplication.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            val user = FirebaseAuth.getInstance().currentUser
            if(user != null)
            {
                val firestoreRef = FirebaseFirestore.getInstance().collection("users")
                val emailKey = user.email?.replace(".","_")

                if(emailKey != null)
                {
                    firestoreRef.document(emailKey).get()
                        .addOnSuccessListener {
                            if(it.exists() && it.contains("username")){
                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                val intent = Intent(this,Profile_Information_Activity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        }
                        .addOnFailureListener {
                            val intent = Intent(this,Profile_Information_Activity::class.java)
                            startActivity(intent)
                            finish()
                        }
                } else {
                    val intent = Intent(this,Profile_Information_Activity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else {
                val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
                finish()
            }
        },3000)
    }
}