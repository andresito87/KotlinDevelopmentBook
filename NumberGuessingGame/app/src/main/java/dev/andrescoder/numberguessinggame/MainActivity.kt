package dev.andrescoder.numberguessinggame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var targetNumber = 0
    private var attempts = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val guessInput = findViewById<EditText>(R.id.guessInput)
        val guessButton = findViewById<Button>(R.id.guessButton)
        val hintText = findViewById<TextView>(R.id.hintText)
        val restartButton = findViewById<Button>(R.id.restartButton)
        startNewGame()
        guessButton.setOnClickListener {
            val guess = guessInput.text.toString().toIntOrNull()
            attempts++
            hintText.text = checkGuess(guess)
            guessInput.text.clear()
        }
        restartButton.setOnClickListener {
            startNewGame()
            hintText.text = "Hint: Start guessing!"
            guessInput.text.clear()
        }
    }
    private fun startNewGame() {
        targetNumber = Random.nextInt(1, 101)
        attempts = 0
    }
    private fun checkGuess(guess: Int?): String {
        if (guess == null || guess !in 1..100) {
            return "Hint: Enter a number between 1 and 100"
        }
        return when {
            guess < targetNumber -> "Hint: Too low! Try again. Attempts: $attempts"
            guess > targetNumber -> "Hint: Too high! Try again. Attempts: $attempts"
            else-> "Congratulations! You got it in $attempts attempts!"
        }
    }
}