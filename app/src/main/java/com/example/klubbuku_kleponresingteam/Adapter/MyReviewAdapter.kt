package com.example.klubbuku_kleponresingteam.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Domain.MyReview
import com.example.klubbuku_kleponresingteam.R

class MyReviewAdapter(private val items: ArrayList<MyReview>) : RecyclerView.Adapter<MyReviewAdapter.ViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.viewholder_myreview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        holder.title.text = items[position].title
        holder.status.text = items[position].status
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.titleTxt)
        val status: TextView = itemView.findViewById(R.id.statusTxt)
    }
}