package com.example.ucar_login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.ucar_login.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_authentication)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        //GO BACK BUTTON
        binding.imageBtnGoBack.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

        //LOGIN BUTTON
        binding.btnLogin.setOnClickListener {

            if(binding.editTextEmail.text.isNotEmpty() && binding.editTextPassword.text.isNotEmpty()){

                // Añadir comprobaciones de datos introducidos...............................................................................................

                logInUser(binding.editTextEmail.text.toString(),binding.editTextPassword.text.toString())

                //val intent = Intent(this, HomeActivity::class.java)
                //startActivity(intent)

            } else {
                binding.textViewResult.visibility = View.VISIBLE
                binding.textViewResult.text = "There cannot be empty fields."
            }
        }

        //ACTION BUTTON FORGOT PASSWORD
        binding.btnForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }

    //LOG IN FUCTION
    private fun logInUser (email: String,password: String ){
        //CREAR FUNCION LOGIN--------------------------------------------------------------------------------------------------------------
    }

}