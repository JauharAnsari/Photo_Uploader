package com.example.photouploader

import android.annotation.SuppressLint
import android.content.Context
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.squareup.picasso.Picasso

class photoAdapter(var context: Context , var  imageList: List<String>):RecyclerView.Adapter<photoAdapter.MyViewHolder>() {
    class MyViewHolder(var itemView : View):RecyclerView.ViewHolder(itemView) {
        val imageUpload = itemView.findViewById<ImageView>(R.id.recyclerImageView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): photoAdapter.MyViewHolder {
       val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recycler_card,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: photoAdapter.MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentItem = imageList[position]

       Picasso.get().load(currentItem).into(holder.imageUpload)

    }

    override fun getItemCount(): Int {
      return imageList.size
    }
}