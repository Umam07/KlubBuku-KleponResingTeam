package com.example.klubbuku_kleponresingteam.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.klubbuku_kleponresingteam.Domain.BukuDomain
import com.example.klubbuku_kleponresingteam.R

class BukuAdapter(private val items: ArrayList<BukuDomain>) : RecyclerView.Adapter<BukuAdapter.BukuViewHolder>() {

    private lateinit var context: Context
    private lateinit var listener: OnItemClickListener

    // Interface untuk handle klik item
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    // Setter untuk listener
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_kategori, parent, false)
        context = parent.context
        return BukuViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        holder.kategoriBuku.text = items[position].kategoriBuku
        holder.priceBuku.text = items[position].priceBuku

        val drawableResourceId = context.resources.getIdentifier(items[position].picPath, "drawable", context.packageName)

        Glide.with(context)
            .load(drawableResourceId)
            .into(holder.pic)

        when (position) {
            0 -> holder.background_buku.setImageResource(R.drawable.bg_1)
            1 -> holder.background_buku.setImageResource(R.drawable.bg_2)
            2 -> holder.background_buku.setImageResource(R.drawable.bg_3)
            3 -> holder.background_buku.setImageResource(R.drawable.bg_3) // Ini mungkin perlu disesuaikan
            4 -> holder.background_buku.setImageResource(R.drawable.bg_4)
        }

        // Set onClickListener untuk setiap item
        holder.itemView.setOnClickListener {
            listener.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // ViewHolder untuk RecyclerView
    inner class BukuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val kategoriBuku: TextView = itemView.findViewById(R.id.kategoriBuku)
        val priceBuku: TextView = itemView.findViewById(R.id.priceBuku)
        val pic: ImageView = itemView.findViewById(R.id.pic)
        val background_buku: ImageView = itemView.findViewById(R.id.background_buku)
    }
}
