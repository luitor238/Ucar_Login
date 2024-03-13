package com.example.ucar_login

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ucar_login.databinding.ActivitySignInStep2Binding
import com.example.ucar_login.databinding.ActivitySignInStep3Binding

class SignInStep3Activity : AppCompatActivity() {

    private lateinit var btnGoBack: ImageButton
    private lateinit var btnNext: Button

    private lateinit var btnCamera: ImageButton
    private lateinit var btnGallery: ImageButton

    private lateinit var Image: ImageView
    private lateinit var Name: EditText

    private lateinit var TextViewResult: TextView

    private lateinit var name: String


    private lateinit var binding: ActivitySignInStep3Binding

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if(uri!=null){
            Image.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_step3)

        binding = ActivitySignInStep3Binding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val username = intent.getStringExtra("Username")
        val password = intent.getStringExtra("Password")
        val email = binding.getStringExtra("Email")
        val phoneNumber = binding.getStringExtra("PhoneNumber")

        btnGoBack = findViewById(R.id.imageBtnGoBack3)
        btnNext = findViewById(R.id.btnNext3)
        btnCamera = findViewById(R.id.btnCamera)
        btnGallery = findViewById(R.id.btnGallery)
        Image = findViewById<ImageView>(R.id.imageUser)
        Name = findViewById<EditText>(R.id.editTextName)
        name = Name.toString()
        TextViewResult = findViewById(R.id.textViewResult)


        //CAMERA BUTTON
        btnCamera.setOnClickListener {
            camera()
        }

        //GALLERY BUTTON
        btnGallery.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        //GO BACK BUTTON
        btnGoBack.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("Password",password)
            startActivity(intent)
        }

        //NEXT BUTTON
        btnNext.setOnClickListener {
            if(name.isNotEmpty()){
                user.setImage(Image)
                user.setName(name)
                val intent = Intent(this, SingInStep2Activity::class.java)
                intent.putExtra("User",user)
                startActivity(intent)
            } else {
                TextViewResult.setTextColor(ContextCompat.getColor(this, R.color.warning))
                TextViewResult.text = "You have to put your name."
            }
        }
    }

    //Funcion Abre la camara
    private fun camera(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,1)

        }
    }

    //Funcion Captura la imagen
    fun OnActivityResult(requestCode: Int, resultCode: Int, data: Intent){
        super.onActivityResult(requestCode,resultCode,data)
        if(requestCode == 1 && resultCode == RESULT_OK){
            val extras = Bundle(data.extras)
            val imgBitmap :Bitmap = extras.get("data") as Bitmap
            Image.setImageBitmap(imgBitmap)
        }
    }
}