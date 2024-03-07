package com.example.ucar_login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.ucar_login.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

        //GO BACK BUTTON
        binding.imageBtnGoBack.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        //LOGIN BUTTON
        try {
            binding.btnLogin.setOnClickListener {

                if(binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()){

                    // Añadir comprobaciones de datos introducidos...............................................................................................

                    auth.signInWithEmailAndPassword(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString())
                        .addOnCompleteListener(this)

                    //val intent = Intent(this, HomeActivity::class.java)
                    //startActivity(intent)

                } else {
                    binding.textViewResult.visibility = View.VISIBLE
                    binding.textViewResult.text = "There cannot be empty fields."
                }
            }
        }
        catch (e: Exception) {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Error")
            builder.setMessage("There is a mistake on the athentication")
            builder.setPositiveButton("OK",null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        //FORGOT PASSWORD BUTTON
        binding.btnForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }
}