package dev.andrescoder.socialfeedapp

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostViewHolder(
    itemView: View,
    sharedPool: RecyclerView.RecycledViewPool,
    private val onItemClick: (Post) -> Unit,
    private val onLikeClick: (Post) -> Unit,
    private val onToggleComments: (Post) -> Unit,
) : RecyclerView.ViewHolder(itemView) {

    private val user: TextView = itemView.findViewById(R.id.user)
    private val content: TextView = itemView.findViewById(R.id.content)
    private val postImage: ImageView = itemView.findViewById(R.id.postImage)
    private val likes: TextView = itemView.findViewById(R.id.likes)
    private val likeButton: Button = itemView.findViewById(R.id.likeButton)
    private val commentsToggle: TextView = itemView.findViewById(R.id.commentsToggle)
    private val commentsList: RecyclerView = itemView.findViewById(R.id.commentsList)

    private val commentsAdapter = CommentsAdapter()
    private var currentPost: Post? = null

    init {
        commentsList.apply {
            layoutManager = LinearLayoutManager(itemView.context)
            adapter = commentsAdapter
            setRecycledViewPool(sharedPool)
            setHasFixedSize(true)
            isNestedScrollingEnabled = false
        }

        itemView.setOnClickListener { currentPost?.let(onItemClick) }
        likeButton.setOnClickListener { currentPost?.let(onLikeClick) }
        commentsToggle.setOnClickListener { currentPost?.let(onToggleComments) }
    }

    fun bind(item: Post, isExpanded: Boolean) {
        currentPost = item

        user.text = item.user
        content.text = item.content
        likes.text = "${item.likes} Likes"

        if (!item.imageUrl.isNullOrBlank()) {
            postImage.isVisible = true
            Glide.with(postImage).load(item.imageUrl).into(postImage)
        } else {
            postImage.isVisible = false
        }

        commentsToggle.text = "Comments (${item.comments.size})"
        commentsList.isVisible = isExpanded
        commentsAdapter.submitList(if (isExpanded) item.comments else emptyList())
    }

    fun bindLikesOnly(newLikes: Int) {
        likes.text = "$newLikes Likes"
    }
}
