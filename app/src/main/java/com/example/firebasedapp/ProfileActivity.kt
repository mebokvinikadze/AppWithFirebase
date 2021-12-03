package com.example.firebasedapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {

    private lateinit var buttonLogout: Button
    private lateinit var buttonChangePassword: Button
    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()

        registerListeners()

        textView.text = FirebaseAuth.getInstance().currentUser?.email

    }

    private fun init() {
        buttonLogout = findViewById(R.id.buttonLogout)
        textView = findViewById(R.id.textView)

    }

    private fun registerListeners() {
        buttonLogout.setOnClickListener() {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()

        }


    }
}

