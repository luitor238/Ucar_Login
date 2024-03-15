package com.example.ucar_login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class HomeActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
/*
    val bundle: Bundle? = intent.extras
    val email: String? = bundle?.getString (key: "email")
    val provider: String? = bundle?.getString (key: "provider")
    setup( email ?: "",  provider ?: "")

    // Guardado de datos
    val prefs = getSharedPreferences()
    }
    private fun setup (email: String, provider: String) {
    title = "Inicio"
    emailTextView.text = email
    providerTextView.text = provider

    logOutButton.setOnClickListener {
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }: View!}*/
}}