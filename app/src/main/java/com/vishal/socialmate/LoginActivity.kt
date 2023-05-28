package com.vishal.socialmate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vishal.myapplication.R

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtLog = findViewById<TextView>(R.id.txt_switch2)
        auth = FirebaseAuth.getInstance()

        txtLog.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        val emailText: TextView = findViewById(R.id.login_email)
        val passText: TextView = findViewById(R.id.login_pass)
        val cPassText: TextView = findViewById(R.id.login_cPass)
        val checkbox: CheckBox = findViewById(R.id.login_checkbox);
        val btn: Button = findViewById(R.id.btn_login)
        val progressBar: ProgressBar = findViewById(R.id.login_progressBar)

        checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                passText.transformationMethod = HideReturnsTransformationMethod.getInstance()
                cPassText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                passText.transformationMethod = PasswordTransformationMethod.getInstance()
                cPassText.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        btn.setOnClickListener {
            val email = emailText.text.toString()
            val pass = passText.text.toString()
            val cPass = cPassText.text.toString()

            if (email.isEmpty()) {
                Toast.makeText(this, "Email can't be Empty \uD83D\uDE1F", Toast.LENGTH_SHORT).show()
            } else if (pass.isEmpty()) {
                Toast.makeText(this, "password can't be Empty \uD83D\uDE1F", Toast.LENGTH_SHORT)
                    .show()
            } else if (cPass.isEmpty()) {
                Toast.makeText(
                    this,
                    "Confirm password can't be Empty \uD83D\uDE1F",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (!email.matches(emailPattern)) {
                Toast.makeText(this, "Invalid Email Address \uD83E\uDD72", Toast.LENGTH_SHORT)
                    .show()
            } else if (pass != cPass) {
                Toast.makeText(
                    this,
                    "Password and confirm password does not match \uD83E\uDEE2",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                progressBar.visibility = View.VISIBLE
                auth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseAuth.getInstance().currentUser
                            val firestoreRef = FirebaseFirestore.getInstance().collection("users")
                            val emailKey = user?.email?.replace(".","_")

                            if(user != null && emailKey != null) {
                                firestoreRef.document(emailKey).get()
                                    .addOnSuccessListener {
                                        if(it.exists() && it.contains("username")) {
                                            // User's profile Information is Already Filled
                                            progressBar.visibility = View.GONE
                                            Toast.makeText(this, "Login successful \uD83D\uDE0D", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this,MainActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        } else {
                                            // User's profile Information is not filled
                                            progressBar.visibility = View.GONE
                                            Toast.makeText(this, "Login successful \uD83D\uDE0D", Toast.LENGTH_SHORT).show()
                                            val intent = Intent(this,Profile_Information_Activity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                    }
                                    .addOnFailureListener {
                                        progressBar.visibility = View.GONE
                                        // Failed to retrieve profile Information
                                        Toast.makeText(this,"Failed to retrieve profile Information",Toast.LENGTH_SHORT).show()
                                    }
                            }
                        } else {
                            progressBar.visibility = View.GONE
                            Toast.makeText(
                                this,
                                "Login Failed: ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

}