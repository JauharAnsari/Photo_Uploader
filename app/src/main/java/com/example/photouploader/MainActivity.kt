package com.example.photouploader

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {
    private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var selectedImageUri: Uri
    companion object {
        private const val IMAGE_PICK_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var upload = findViewById<Button>(R.id.upload)
       var buttonTwo = findViewById<Button>(R.id.buttonTwo)
        var selectImage = findViewById<ImageView>(R.id.selectImage)

        storageReference = FirebaseStorage.getInstance().reference.child("images")
        databaseReference = FirebaseDatabase.getInstance().reference.child("image_urls")



        selectImage.setOnClickListener {
            selectImageFromGallary()
        }
        upload.setOnClickListener {
            uploadImageToFirebase()
        }


        buttonTwo.setOnClickListener {
            var intent = Intent(this,photoActivity::class.java)
            startActivity(intent)
        }

         }

    private fun uploadImageToFirebase() {
        if (::selectedImageUri.isInitialized) {
            val imageRef = storageReference.child(selectedImageUri.lastPathSegment!!)
            imageRef.putFile(selectedImageUri).addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        databaseReference.push().setValue(imageUrl)
                        Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Please select an image first", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!      // data.data is URI

        }
    }

    private fun selectImageFromGallary() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_REQUEST)

    }


}
