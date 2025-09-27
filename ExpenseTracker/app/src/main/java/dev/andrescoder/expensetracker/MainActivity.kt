package dev.andrescoder.expensetracker

import android.content.SharedPreferences
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private val expenses = mutableListOf<Expense>()
    private lateinit var prefs: SharedPreferences
    private val formatter = DecimalFormat("$#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        prefs = getSharedPreferences("expense_prefs", MODE_PRIVATE)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val amountInput = findViewById<EditText>(R.id.amountInput)
        val descriptionInput = findViewById<EditText>(R.id.descriptionInput)
        val addButton = findViewById<Button>(R.id.addButton)
        val exportButton = findViewById<Button>(R.id.exportButton)
        val summaryText = findViewById<TextView>(R.id.summaryText)
        loadExpenses()
        updateSummary()
        addButton.setOnClickListener {
            val amount = amountInput.text.toString().toDoubleOrNull()
            val description = descriptionInput.text.toString()
            if (amount != null && description.isNotBlank()) {
                val expense = Expense(
                    amount,
                    description,
                    System.currentTimeMillis()
                )
                expenses.add(expense)
                saveExpenses()
                updateSummary()
                amountInput.text.clear()
                descriptionInput.text.clear()
            }
        }
        exportButton.setOnClickListener { exportExpenses() }
    }

    private fun saveExpenses() {
        val expenseStrings = expenses.joinToString(";") {
            "${it.amount}|${it.description}|${it.timestamp}"
        }
        prefs.edit().apply {
            putString(
                "expenses",
                expenseStrings
            )
            apply()
        }
    }

    private fun loadExpenses() {
        val expenseString = prefs.getString("expenses", "") ?: return
        expenseString.split(";")
            .filter { it.isNotBlank() }.forEach {
                val parts = it.split("|")
                if (parts.size == 3) {
                    expenses.add(Expense(parts[0].toDouble(), parts[1], parts[2].toLong()))
                }
            }
    }

    private fun updateSummary() {
        val total = expenses.sumOf { it.amount }
        findViewById<TextView>(R.id.summaryText).text = "Total: ${formatter.format(total)}"
    }

    private fun exportExpenses() {
        val file = File(filesDir, "expenses.txt")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val content = expenses.joinToString("\n") {
            "${formatter.format(it.amount)} - ${it.description} (${
                dateFormat.format(Date(it.timestamp))
            })"
        }
        file.writeText(content)
        Toast.makeText(this, "Exported to ${file.absolutePath}", Toast.LENGTH_LONG).show()
    }
}
