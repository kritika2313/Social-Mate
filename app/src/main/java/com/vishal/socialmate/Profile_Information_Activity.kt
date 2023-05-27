package com.vishal.socialmate

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.squareup.picasso.Picasso
import com.vishal.myapplication.R
import de.hdodenhof.circleimageview.CircleImageView

class Profile_Information_Activity : AppCompatActivity() {

    private var PICK_IMAGE = 123

    private lateinit var circularImage: CircleImageView

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

        genderMale.setOnClickListener {
            genderMale.setBackgroundResource(R.drawable.malefemalefocus)
            genderFemale.setBackgroundResource(R.drawable.malefeamlenotfocus)
        }

        genderFemale.setOnClickListener {
            genderFemale.setBackgroundResource(R.drawable.malefemalefocus)
            genderMale.setBackgroundResource(R.drawable.malefeamlenotfocus)
        }

        circularImage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, PICK_IMAGE)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE || resultCode == RESULT_OK || data != null) {
           val selectedImageUri = data?.data
            if (data != null) {
                Picasso.get().load(selectedImageUri).into(circularImage)
            } else {
                Toast.makeText(this, "Failed to load Image \uD83D\uDE1F", Toast.LENGTH_SHORT)
                    .show()
            }

        }
    }
}



