package dev.andrescoder.tipcalculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val billAmount = findViewById<EditText>(R.id.billAmount)
        val tipOptions = findViewById<RadioGroup>(R.id.tipOptions)
        val calculateButton = findViewById<Button>(R.id.calculateButton)
        val tipResult = findViewById<TextView>(R.id.tipResult)
        val totalResult = findViewById<TextView>(R.id.totalResult)

        calculateButton.setOnClickListener {
            val bill = billAmount.text.toString().toDoubleOrNull()

            if (bill == null || bill <= 0) {
                tipResult.text = "Tip: Invalid input"
                totalResult.text = "Total: Invalid input"

                return@setOnClickListener
            }

            val tipPercentage = when (tipOptions.checkedRadioButtonId) {
                R.id.tip10 -> 0.10
                R.id.tip15 -> 0.15
                R.id.tip20 -> 0.20
                else -> 0.15
            }
            val tip = calculateTip(bill, tipPercentage)
            val total = bill + tip
            val formatter =
                DecimalFormat("$#,##0.00")
            tipResult.text = "Tip: ${formatter.format(tip)}"
            totalResult.text = "Total: ${formatter.format(total)}"

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }
    }

    private fun calculateTip(bill: Double, percentage: Double): Double = bill * percentage
}