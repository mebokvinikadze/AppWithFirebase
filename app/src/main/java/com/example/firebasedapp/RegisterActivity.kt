package com.example.firebasedapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {

    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextRepeatPassword: EditText
    private lateinit var buttonRegister: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

        registerListeners()

    }

    private fun init() {
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword)
        buttonRegister = findViewById(R.id.buttonRegister)

    }

    private fun registerListeners() {
        buttonRegister.setOnClickListener() {
            if (validateInput()) {
                val email = editTextEmail.text.toString()
                val password = editTextPassword.text.toString()




                FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(
                                this,
                                "This email is already registered!",
                                Toast.LENGTH_SHORT
                            )
                                .show()

                        }
                    }
            }
        }
    }

    fun validateInput(): Boolean {

        // checking if email isn't empty
        if (editTextEmail.text.toString().equals("")) {
            editTextEmail.setError("Please Enter Email")
            return false
        }

        // checking if password isn't empty
        if (editTextPassword.text.toString().equals("")) {
            editTextPassword.setError("Please Enter Password")
            return false
        }

        // checking if password isn't empty
        if (editTextRepeatPassword.text.toString().equals("")) {
            editTextRepeatPassword.setError("Repeat Password")
            return false
        }

        // checking the proper email format
        if (!isEmailValid(editTextEmail.text.toString())) {
            editTextEmail.setError("Please Enter Valid Email")
            return false
        }

        // checking minimum password Length
        if (editTextPassword.text.length < 9) {
            editTextPassword.setError("Password Length must be more than " + 9 + "characters")
            return false
        }

        // password must contain
        if (!isValidPassword(editTextPassword.text.toString())) {
            editTextPassword.setError("Password must contain a-z , 0-9")
            return false
        }

        // Checking if repeat password is the same
        if (!editTextRepeatPassword.text.toString().equals(editTextPassword.text.toString())) {
            editTextRepeatPassword.setError("Password does not match")
            return false
        }
        return true
    }

    // valid email function
    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // extension function to validate password rules/patterns
    fun isValidPassword(password: String?): Boolean {
        password?.let {
            val passwordPattern = "(?=.*[a-z])(?=.*[0-9])"
            val passwordMatcher = Regex(passwordPattern)
            return passwordMatcher.find(password) != null
        } ?: return false

    }


}

