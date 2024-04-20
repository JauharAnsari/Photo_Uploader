package com.example.photouploader

import android.annotation.SuppressLint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.widget.Button
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class photoActivity : AppCompatActivity() {
    lateinit var Myadapter: photoAdapter
    private lateinit var databaseReference: DatabaseReference
    lateinit var myRecyclerView: RecyclerView
    private lateinit var imageList: MutableList<String>

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo)
        myRecyclerView = findViewById(R.id.myRecyclerView)


        myRecyclerView.layoutManager = LinearLayoutManager(this)
        imageList = mutableListOf()
        Myadapter = photoAdapter(this, imageList)
        myRecyclerView.adapter = Myadapter


        databaseReference = FirebaseDatabase.getInstance().reference.child("image_urls")
        // Retrieve image URLs from Firebase Realtime Database
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                imageList.clear()
                for (postSnapshot in snapshot.children) {
                    val imageUrl = postSnapshot.getValue(String::class.java)
                    if (imageUrl != null) {
                        imageList.add(imageUrl)
                    }
                }
               Myadapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@photoActivity, "Failed to load images", Toast.LENGTH_SHORT).show()
            }
        })

    }


}