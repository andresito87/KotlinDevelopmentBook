package dev.andrescoder.socialfeedapp

data class Post(
    val id:Int,
    val user:String,
    val content:String,
    val likes:Int,
    val imageUrl: String? = null,
    val comments: List<Comment> = emptyList()
)
