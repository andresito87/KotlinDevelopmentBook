package dev.andrescoder.socialfeedapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class CommentsAdapter : ListAdapter<Comment, CommentsAdapter.VH>(CommentDiff) {

    init { setHasStableIds(true) }
    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    class VH(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
    ) {
        val author: TextView = itemView.findViewById(R.id.author)
        val text: TextView = itemView.findViewById(R.id.text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(parent)

    override fun onBindViewHolder(holder: VH, position: Int) {
        val c = getItem(position)
        holder.author.text = c.author
        holder.text.text = c.text
    }
}
