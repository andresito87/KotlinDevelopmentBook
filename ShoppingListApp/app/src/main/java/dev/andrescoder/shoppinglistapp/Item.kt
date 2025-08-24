package dev.andrescoder.shoppinglistapp

data class Item(
    val name: String,
    val category: String,
    val quantity: Int = 1,
)
