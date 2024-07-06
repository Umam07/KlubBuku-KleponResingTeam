// ReviewAdapter.kt
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
        val profileImage: ImageView = itemView.findViewById(R.id.profile_image)
        val profileName: TextView = itemView.findViewById(R.id.profile_name)
        val postTime: TextView = itemView.findViewById(R.id.post_time)
        val postContent: TextView = itemView.findViewById(R.id.post_content)
        val postImage: ImageView = itemView.findViewById(R.id.post_image)
        val likesCount: TextView = itemView.findViewById(R.id.likes_count)
        val commentsCount: TextView = itemView.findViewById(R.id.comments_count)
        val sharesCount: TextView = itemView.findViewById(R.id.shares_count)
        val judul: TextView = itemView.findViewById(R.id.judul)
        val genre: TextView = itemView.findViewById(R.id.genre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reviewList[position]

        // Assume profileImage, profileName, and postTime are part of Review model
        holder.profileName.text = currentItem.penulis
        holder.postTime.text = "1 jam" // Static for example
        holder.postContent.text = currentItem.review
        holder.genre.text = currentItem.genre
        holder.judul.text = currentItem.judul
        Picasso.get().load(currentItem.imageUrl).into(holder.postImage)
        holder.likesCount.text = "2,5rb" // Static for example
        holder.commentsCount.text = "122" // Static for example
        holder.sharesCount.text = "18rb" // Static for example
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }
}
