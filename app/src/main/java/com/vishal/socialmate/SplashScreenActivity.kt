package com.vishal.socialmate

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vishal.myapplication.R
import com.vishal.socialmate.InternetConnectvity.checkForInternet

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var dialog : AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        if(InternetConnectvity.checkForInternet(this)){
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

        } else {
            showNoInternetDialog();
        }


    }

    @SuppressLint("MissingInflatedId")
    private fun showNoInternetDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_no_internet,null)
        val tryAgainButton = dialogView.findViewById<Button>(R.id.try_again_btn)
        val closeButton = dialogView.findViewById<Button>(R.id.close_btn)

        tryAgainButton.setOnClickListener {
            dialog.dismiss()
            recreate()
        }

        closeButton.setOnClickListener {
            finish()
        }

        val dialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)

        dialog = dialogBuilder.create()
        dialog.show()
    }
}