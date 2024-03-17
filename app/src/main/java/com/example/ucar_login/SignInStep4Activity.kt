package com.example.ucar_login

import android.content.ContentValues
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ucar_login.databinding.ActivitySignInStep4Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

class SignInStep4Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInStep4Binding
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_step4)

        Log.d(ContentValues.TAG, "Estoy en la siguietne actividad")


        binding = ActivitySignInStep4Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = FirebaseAuth.getInstance()


        val username = intent.getStringExtra("Username")
        val password = intent.getStringExtra("Password")
        val email = intent.getStringExtra("Email")
        val phoneNumber = intent.getStringExtra("PhoneNumber")
        val name = intent.getStringExtra("Name")
       // val imagePath = intent.getStringExtra("Image")




/*
        if (imagePath != null) {
            if (imagePath.isNotEmpty()) {
                val imageFile = File(imagePath)
                if (imageFile.exists()) {
                    val image = BitmapFactory.decodeFile(imageFile.absolutePath)
                }
            }
        }*/

        //GO BACK BUTTON
        binding.imageBtnGoBack.setOnClickListener {
            val intent = Intent(this, SignInStep3Activity::class.java)
            intent.putExtra("Username", username)
            intent.putExtra("Password", password)
            intent.putExtra("Email", email)
            intent.putExtra("PhoneNumber", phoneNumber)
            startActivity(intent)
        }

        //NEXT BUTTON

        try {
            binding.btnCreate.setOnClickListener{
                Log.d(ContentValues.TAG, "Aqui estoy ")
                if (email != null && password != null ) {
                    Log.d(ContentValues.TAG, "no es nulo ")
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            Log.d(ContentValues.TAG, "no es vacio ")


                            auth.createUserWithEmailAndPassword(email.toString(), password.toString()).addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    val bibliography = binding.editTextBibliography.text.toString()
                                    Log.d(ContentValues.TAG, "El usuario registrado correctamente. ")
                                    db.collection("users").document(email).set(
                                        hashMapOf("usuario" to username,
                                            "phoneNumber" to phoneNumber,
                                            "name" to name,
                                            "bibliography" to bibliography
                                            )
                                    )
                                    val intent = Intent(this, HomeActivity::class.java)
                                    startActivity(intent)
                                } else { Log.d(ContentValues.TAG, "El usuario no fue registrado. Manejar el error apropiadamente") }

                            }
                        }else {Log.d(ContentValues.TAG, "El correo tiene un error de formato") }


                }
            }





            } catch (e: Exception) {
                Log.d(ContentValues.TAG, "Error no esperado")
            }

            //username, password, email, phoneNumber, name, image, bibliography

            // añadir datops recogidos a firebase-----------------------------------------MARCO!!----------------------------------------


        }


}