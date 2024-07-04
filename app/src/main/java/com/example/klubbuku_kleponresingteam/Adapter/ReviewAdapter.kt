package com.example.klubbuku_kleponresingteam.Activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Domain.Review
import com.example.klubbuku_kleponresingteam.R
import com.squareup.picasso.Picasso

class ReviewAdapter(private val reviewList: ArrayList<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judul: TextView = itemView.findViewById(R.id.itemJudul)
        val penulis: TextView = itemView.findViewById(R.id.itemPenulis)
        val genre: TextView = itemView.findViewById(R.id.itemGenre)
        val review: TextView = itemView.findViewById(R.id.itemReview)
        val image: ImageView = itemView.findViewById(R.id.itemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reviewList[position]

        holder.judul.text = currentItem.judul
        holder.penulis.text = currentItem.penulis
        holder.genre.text = currentItem.genre
        holder.review.text = currentItem.review
        Picasso.get().load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}
