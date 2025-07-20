package dev.andrescoder.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity

class MainActivity : ComponentActivity() {

    private val questions: List<Question> = listOf(
        Question("What is 2+2?", listOf("3", "4", "5", "6"), 1),
        Question("Capital of France?", listOf("Paris", "London", "Berlin", "Rome"), 0),
        Question("Which planet is the Red Planet?", listOf("Earth", "Mars", "Jupiter", "Venus"), 1),
        Question("Leap year has how many days?", listOf("365", "366", "364", "360"), 1),
        Question("Mix red and white gives?", listOf("Purple", "Pink", "Orange", "Brown"), 1)
    )
    private var currentQuestion = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val questionText = findViewById<TextView>(R.id.questionText)
        val answerOptions = findViewById<RadioGroup>(R.id.answerOptions)
        val option1 = findViewById<RadioButton>(R.id.option1)
        val option2 = findViewById<RadioButton>(R.id.option2)
        val option3 = findViewById<RadioButton>(R.id.option3)
        val option4 = findViewById<RadioButton>(R.id.option4)
        val submitButton = findViewById<Button>(R.id.submitButton)

        fun displayQuestion() {
            if (currentQuestion < questions.size) {
                val q = questions[currentQuestion]
                questionText.text = q.text
                option1.text = q.options[0]
                option2.text = q.options[1]
                option3.text = q.options[2]
                option4.text = q.options[3]
                answerOptions.clearCheck()
            } else {
                Intent(this, ResultActivity::class.java).also { intent ->
                    intent.putExtra("score", score)
                    intent.putExtra("total", questions.size)
                    startActivity(intent)
                    finish()
                }
            }
        }

        displayQuestion()

        submitButton.setOnClickListener {
            val selectedId = answerOptions.checkedRadioButtonId
            if (selectedId != -1) {
                val selectedIndex = answerOptions.indexOfChild(findViewById(selectedId))
                if (selectedIndex == questions[currentQuestion].correctAnswer) {
                    score++
                }
                currentQuestion++
                displayQuestion()
            } else {
                Toast.makeText(this, "Select an answer", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
