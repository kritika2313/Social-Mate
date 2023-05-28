package com.vishal.socialmate

import android.annotation.SuppressLint
import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import com.vishal.myapplication.R
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class Profile_Information_Activity : AppCompatActivity() {

    private var PICK_IMAGE = 123

    private lateinit var circularImage: CircleImageView
    private var gender : String = "Male"

     private var selectedImageUri : Uri? = null
    private var defaultImageUri = "https://firebasestorage.googleapis.com/v0/b/social-mate-4b1c8.appspot.com/o/profile.png?alt=media&token=b64b5a5f-0b97-454c-99b2-fb578b2a7bf3"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_information)

        circularImage = findViewById(R.id.cir_img_view)
        val userNameP = findViewById<EditText>(R.id.pro_inf_userName)
        val headlineP = findViewById<EditText>(R.id.pro_inf_headline)
        val cityName = findViewById<EditText>(R.id.pro_inf_city)
        val professionName = findViewById<EditText>(R.id.pro_inf_profession);

        val genderMale = findViewById<LinearLayout>(R.id.ll_male)
        val genderFemale = findViewById<LinearLayout>(R.id.ll_female)

        val updateBtn = findViewById<Button>(R.id.pro_inf_update_btn)

        val progressbar = findViewById<ProgressBar>(R.id.pro_inf_progressbar)

        genderMale.setOnClickListener {
            genderMale.setBackgroundResource(R.drawable.malefemalefocus)
            genderFemale.setBackgroundResource(R.drawable.malefeamlenotfocus)
            gender = "Male"
        }

        genderFemale.setOnClickListener {
            genderFemale.setBackgroundResource(R.drawable.malefemalefocus)
            genderMale.setBackgroundResource(R.drawable.malefeamlenotfocus)
            gender = "Female"
        }

        circularImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE)
        }

        val user = FirebaseAuth.getInstance().currentUser

        updateBtn.setOnClickListener {
            val username = userNameP.text.toString()
            val headline = headlineP.text.toString()
            val city = cityName.text.toString()
            val profession = professionName.text.toString()
            progressbar.visibility = View.VISIBLE

            if(username.isEmpty()){
                progressbar.visibility = View.GONE
                Toast.makeText(this,"Username can't be empty \uD83E\uDD72",Toast.LENGTH_SHORT).show()
            } else if(headline.isEmpty()){
                progressbar.visibility = View.GONE
                Toast.makeText(this,"Headline can't be empty \uD83E\uDD72",Toast.LENGTH_SHORT).show()
            } else if(city.isEmpty()){
                progressbar.visibility = View.GONE
                Toast.makeText(this,"City name can't be empty \uD83E\uDD72",Toast.LENGTH_SHORT).show()
            } else if(profession.isEmpty()){
                progressbar.visibility = View.GONE
                Toast.makeText(this,"Profession can't be empty \uD83E\uDD72",Toast.LENGTH_SHORT).show()
            } else {
                if(selectedImageUri != null){

                    val storageRef = FirebaseStorage.getInstance().reference
                    val imageRef = storageRef.child("profileImages/${UUID.randomUUID()}")

                    imageRef.putFile(selectedImageUri!!)
                        .addOnSuccessListener {
                            imageRef.downloadUrl
                                .addOnSuccessListener {  uri ->
                                    progressbar.visibility = View.VISIBLE
                                    val imageUrl = uri.toString()
                                    val userProfile = UserProfile(username,headline,city,profession,gender,imageUrl)

                                    val userEmail = user?.email
                                    val emailKey = userEmail?.replace(".","_")

                                    // Save to firebase realtime database
                                    val databaseRef = FirebaseDatabase.getInstance().reference.child("users")
                                    emailKey?.let {
                                        databaseRef.child(emailKey).setValue(userProfile)
                                    }

                                    // save to firestore database
                                    val firestoreRef = FirebaseFirestore.getInstance().collection("users")
                                    emailKey?.let { firestoreRef.document(it).set(userProfile) }

                                    progressbar.visibility = View.GONE
                                    Toast.makeText(this,"Profile Information Saved Successfully \uD83D\uDCA5",Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this,MainActivity::class.java)
                                    startActivity(intent)
                                    finish()

                                }
                                .addOnFailureListener {
                                    Toast.makeText(this,"Failed to Upload Image: ${it.message}",Toast.LENGTH_SHORT).show()
                                    progressbar.visibility = View.GONE
                                }
                        }
                        .addOnFailureListener {
                            Toast.makeText(this,"Failed to Upload Image: ${it.message}",Toast.LENGTH_SHORT).show()
                            progressbar.visibility = View.GONE
                        }
                } else {
                    progressbar.visibility = View.VISIBLE
                    val defUserprofile = UserProfile(username,headline,city,profession,gender,defaultImageUri)

                    val userEmail = user?.email
                    val emailKey = userEmail?.replace(".","_")

                    // Save to firebase realtime database
                    val databaseRef = FirebaseDatabase.getInstance().reference.child("users")
                    emailKey?.let {
                        databaseRef.child(emailKey).setValue(defUserprofile)

                        // save to firestore database
                        val firestoreRef = FirebaseFirestore.getInstance().collection("users")
                        emailKey?.let { firestoreRef.document(it).set(defUserprofile)
                            .addOnSuccessListener {
                                Toast.makeText(this,"Profile Information Saved Successfully \uD83D\uDCA5",Toast.LENGTH_SHORT).show()
                                progressbar.visibility = View.GONE

                                val intent = Intent(this,MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this,"Failed to Update Information",Toast.LENGTH_SHORT).show()
                                progressbar.visibility = View.GONE
                            }
                        }


                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
                selectedImageUri = data.data

            if (selectedImageUri != null) {
                Picasso.get().load(selectedImageUri).into(circularImage)
            } else {
                Toast.makeText(this, "Failed to load Image \uD83D\uDE1F", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}


