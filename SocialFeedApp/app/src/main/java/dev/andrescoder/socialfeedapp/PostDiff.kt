package dev.andrescoder.socialfeedapp

import androidx.recyclerview.widget.DiffUtil

object PostDiff : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

    // Payload si solo cambian likes (update parcial)
    override fun getChangePayload(oldItem: Post, newItem: Post): Any? {
        return if (oldItem.copy(likes = 0) == newItem.copy(likes = 0)
            && oldItem.likes != newItem.likes
        ) PAYLOAD_LIKES else null
    }

    const val PAYLOAD_LIKES = "likes_changed"
}