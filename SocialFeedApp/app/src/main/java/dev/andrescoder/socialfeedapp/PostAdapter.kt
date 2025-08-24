package dev.andrescoder.socialfeedapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(
    private val onLike: (Post) -> Unit,
    private val onItemClick: (Post) -> Unit = {},
) : ListAdapter<Post, PostAdapter.VH>(PostDiff) {

    // Para que los RecycleViews anidados (comments) compartan pool y sean más eficientes
    private val sharedPool = RecyclerView.RecycledViewPool()

    // Guardamos qué posts tienen comentarios visibles (estado UI, fuera del modelo)
    private val expanded = mutableSetOf<Long>()

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    inner class VH(view: View) : RecyclerView.ViewHolder(view) {
        val user: TextView = view.findViewById(R.id.user)
        val content: TextView = view.findViewById(R.id.content)
        val postImage: ImageView = view.findViewById(R.id.postImage)
        val likes: TextView = view.findViewById(R.id.likes)
        val likeButton: Button = view.findViewById(R.id.likeButton)
        val commentsToggle: TextView = view.findViewById(R.id.commentsToggle)
        val commentsList: RecyclerView = view.findViewById(R.id.commentsList)
        val commentsAdapter = CommentsAdapter()

        init {
            // Click al item (post)
            itemView.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(pos))
                }
            }

            // RecyclerView anidado (comments)
            commentsList.apply {
                layoutManager = LinearLayoutManager(view.context)
                adapter = commentsAdapter
                setRecycledViewPool(sharedPool)
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
            }
        }

        fun bind(item: Post) {
            user.text = item.user
            content.text = item.content
            likes.text = "${item.likes} Likes"

            // Imagen
            if (!item.imageUrl.isNullOrBlank()) {
                postImage.isVisible = true
                Glide.with(postImage).load(item.imageUrl).into(postImage)
            } else {
                postImage.isVisible = false
            }

            // Comentarios
            commentsToggle.text = "Comments (${item.comments.size})"
            val key = item.id.toLong()
            val isOpen = expanded.contains(key)

            commentsList.isVisible = isOpen
            if (isOpen) commentsAdapter.submitList(item.comments)

            likeButton.setOnClickListener { onLike(item) }
            commentsToggle.setOnClickListener {
                if (expanded.contains(key)) {
                    expanded.remove(key)
                    commentsList.isVisible = false
                } else {
                    expanded.add(key)
                    commentsAdapter.submitList(item.comments)
                    commentsList.isVisible = true
                }
            }
        }

        fun bindLikesOnly(newLikes: Int) {
            likes.text = "$newLikes Likes"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        if (payloads.contains(PostDiff.PAYLOAD_LIKES)) {
            holder.bindLikesOnly(getItem(position).likes)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}
