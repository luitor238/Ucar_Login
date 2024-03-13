package com.example.ucar_login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.ucar_login.databinding.ActivityLogInBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var googleSignInClient: GoogleSignInClient
    private var RC_SIGN_IN = 20

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //LOGIN GOOGLE
        binding.btnLoginGoogle.setOnClickListener {
            googleSignIn()
        }
        //LOGIN FACEBOOK
        binding.btnLoginFacebook.setOnClickListener {


        }
        //LOGIN MANUAL
        binding.btnLoginManual.setOnClickListener {
            binding.viewLoginUser.visibility = View.VISIBLE

            // Animación de escala en X
            val scaleX = ObjectAnimator.ofFloat(binding.viewLoginUser, View.SCALE_X, 0f, 1f)
            scaleX.duration = 500

            // Animación de escala en Y
            val scaleY = ObjectAnimator.ofFloat(binding.viewLoginUser, View.SCALE_Y, 0f, 1f)
            scaleY.duration = 500

            // Establecer el punto central de la vista como punto de escala
            binding.viewLoginUser.pivotX = (binding.viewLoginUser.width / 2).toFloat()
            binding.viewLoginUser.pivotY = (binding.viewLoginUser.height / 2).toFloat()

            // Crear un conjunto de animaciones y ejecutarlas
            val animatorSet = AnimatorSet()
            animatorSet.play(scaleX).with(scaleY)
            animatorSet.start()
        }
        //SING IN MANUAL
        binding.btnSingInManual.setOnClickListener {
            val intent = Intent(this, SignInStep1Activity::class.java)
            startActivity(intent)
        }


    // ---------------------------------- AUTHENTICATION ----------------------------------

        //GO BACK BUTTON
        binding.imageBtnGoBack.setOnClickListener {

            // Crear un objeto de animación de transparencia
            val fadeOut = ObjectAnimator.ofFloat(binding.viewLoginUser, View.ALPHA, 1f, 0f)
            fadeOut.duration = 1000 // Duración de la animación en milisegundos

            // Iniciar la animación
            fadeOut.start()
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

    private fun googleSignIn() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { firebaseAuth(it) }
            } catch (e: ApiException) {
                Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAuth(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val map = HashMap<String, Any>()
                    map["id"] = user!!.uid
                    map["name"] = user.displayName!!
                    map["profile"] = user.photoUrl.toString()

                    database.reference.child("users").child(user.uid).setValue(map)
                    val intent = Intent(this, SignInStep1Activity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
