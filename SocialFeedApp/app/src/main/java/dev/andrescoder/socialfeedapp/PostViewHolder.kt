package dev.andrescoder.socialfeedapp

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val user: TextView = itemView.findViewById(R.id.user)
    val content: TextView = itemView.findViewById(R.id.content)
    val likes: TextView = itemView.findViewById(R.id.likes)
    val likeButton: Button = itemView.findViewById(R.id.likeButton)
}
