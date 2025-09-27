package dev.andrescoder.expensetracker

data class Expense(
    val amount: Double,
    val description: String,
    val timestamp: Long,
)
