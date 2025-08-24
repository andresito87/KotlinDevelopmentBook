package dev.andrescoder.socialfeedapp

import androidx.recyclerview.widget.DiffUtil

object CommentDiff : DiffUtil.ItemCallback<Comment>() {
    override fun areItemsTheSame(old: Comment, new: Comment) = old.id == new.id
    override fun areContentsTheSame(old: Comment, new: Comment) = old == new
}

