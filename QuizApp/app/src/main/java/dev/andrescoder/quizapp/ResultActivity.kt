package dev.andrescoder.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val score = intent.getIntExtra("score", 0)
        val total = intent.getIntExtra("total", 0)

        findViewById<TextView>(R.id.scoreText).text = "Score: $score / $total"

        findViewById<Button>(R.id.btnRestart).setOnClickListener {
            // Reset the quiz and restart MainActivity
            Intent(this, MainActivity::class.java).also { restart ->
                restart.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(restart)
                finish()
            }
        }
    }
}
