package com.example.ucar_login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.ucar_login.databinding.ActivitySignInStep3Binding

class SignInStep3Activity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInStep3Binding

    lateinit var uria: Uri

    val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){uri ->
        if(uri!=null){
            binding.imageUser.setImageURI(uri)
            uria = uri
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
        val email = intent.getStringExtra("Email")
        val phoneNumber = intent.getStringExtra("PhoneNumber")
        val image = binding.imageUser
        val name = binding.editTextName.text.toString()


        //CAMERA BUTTON
        binding.btnCamera.setOnClickListener {
            camera()
        }

        //GALLERY BUTTON
        binding.btnGallery.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        //GO BACK BUTTON
        binding.imageBtnGoBack.setOnClickListener {
            val intent = Intent(this, SignInStep2Activity::class.java)
            intent.putExtra("Username",username)
            intent.putExtra("Password",password)
            startActivity(intent)
        }

        //NEXT BUTTON
        binding.btnNext.setOnClickListener {
            if(name.isNotEmpty()){
                val uri = uria

                // Guarda la ruta de la imagen seleccionada
                val imagePath = getImagePath(uri)

                // Pasar la ruta de la imagen a la siguiente actividad
                val intent = Intent(this, SignInStep4Activity::class.java)
                intent.putExtra("Username", username)
                intent.putExtra("Password", password)
                intent.putExtra("Email", email)
                intent.putExtra("PhoneNumber", phoneNumber)
                intent.putExtra("Image", imagePath)
                intent.putExtra("Name", name)
                startActivity(intent)
            } else {
                binding.textViewResult.setTextColor(ContextCompat.getColor(this, R.color.warning))
                binding.textViewResult.text = "You have to put your name."
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
            binding.imageUser.setImageBitmap(imgBitmap)
        }
    }

    // Función para obtener la ruta de la imagen a partir del URI
    @SuppressLint("Range")
    private fun getImagePath(uri: Uri): String {
        val cursor = contentResolver.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        val imagePath = cursor?.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA))
        cursor?.close()
        return imagePath ?: ""
    }
}


/*
companion object {
        private const val REQUEST_CODE_PICK_IMAGE = 1001
    }


val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE)


override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val inputStream = contentResolver.openInputStream(uri)
                val bitmap = BitmapFactory.decodeStream(inputStream)

                // Convertir el Bitmap a ByteArray
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val byteArray = stream.toByteArray()

                val intent = Intent(this, OtraActividad::class.java)
                intent.putExtra("imagen", byteArray)
                startActivity(intent)
            }
        }
    }*/