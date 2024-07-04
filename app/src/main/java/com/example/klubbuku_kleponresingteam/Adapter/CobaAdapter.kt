package com.example.klubbuku_kleponresingteam.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.klubbuku_kleponresingteam.Domain.CobaDomain
import com.example.klubbuku_kleponresingteam.R
import com.squareup.picasso.Picasso

class CobaAdapter(private val context: Context, private val bookList: List<CobaDomain>) :
    RecyclerView.Adapter<CobaAdapter.BookViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_coba, parent, false)
        return BookViewHolder(view)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = bookList[position]
        holder.bookTitle.text = book.title
        holder.bookAuthor.text = book.author
        holder.bookRating.rating = book.rating
        holder.bookRatingValue.text = book.rating.toString()
        holder.bookRatingsCount.text = String.format("%,d ratings", book.ratingCount)
        Picasso.get().load(book.coverUrl).into(holder.bookCover)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bookCover: ImageView = itemView.findViewById(R.id.book_cover)
        val bookTitle: TextView = itemView.findViewById(R.id.book_title)
        val bookAuthor: TextView = itemView.findViewById(R.id.book_author)
        val bookRating: RatingBar = itemView.findViewById(R.id.book_rating)
        val bookRatingValue: TextView = itemView.findViewById(R.id.book_rating_value)
        val bookRatingsCount: TextView = itemView.findViewById(R.id.book_ratings_count)
        val wantToReadButton: Button = itemView.findViewById(R.id.want_to_read_button)
    }
}
