package dev.andrescoder.todolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var taskInput: EditText
    private lateinit var addButton: Button
    private lateinit var taskList: ListView
    private lateinit var adapter: TaskAdapter
    private val tasks = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ensure you have this extension available
        setContentView(R.layout.activity_main)

        // Apply window insets for edge-to-edge layouts
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        taskInput = findViewById(R.id.taskInput)
        addButton = findViewById(R.id.addButton)
        taskList = findViewById(R.id.taskList)

        adapter = TaskAdapter(
            this, tasks,
            onDelete = { pos ->
                tasks.removeAt(pos)
                updateTaskList()
            },
            onToggle = { pos ->
                tasks[pos].isCompleted = !tasks[pos].isCompleted
                updateTaskList()
            }
        )
        taskList.adapter = adapter

        // Add button click listener
        addButton.setOnClickListener {
            val description = taskInput.text.toString()
            if (description.isNotBlank()) {
                val task = Task(description)
                tasks.add(task)
                updateTaskList()
                taskInput.text.clear()
            }
        }

        // Toggle completion status on item click
        taskList.setOnItemClickListener { _, _, position, _ ->
            tasks[position].isCompleted = !tasks[position].isCompleted
            updateTaskList()
        }
    }

    // Refresh the list view with updated strings
    private fun updateTaskList() {
        adapter.notifyDataSetChanged()
    }
}
