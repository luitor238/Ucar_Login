package com.example.ucar_login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.ucar_login.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //LOGIN GOOGLE
        binding.btnLoginGoogle.setOnClickListener {
            // MARCO AÑADE FUNCION!!------------------------------------------------------------------------------------------------------------
        }
        //LOGIN FACEBOOK
        binding.btnLoginFacebook.setOnClickListener {
            // MARCO AÑADE FUNCION!!------------------------------------------------------------------------------------------------------------
        }
        //LOGIN MANUAL
        binding.btnLoginManual.setOnClickListener {
            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
        }
        //SING IN MANUAL
        binding.btnSingInManual.setOnClickListener {
            val intent = Intent(this, SingInStep1Activity::class.java)
            startActivity(intent)
        }
    }
}