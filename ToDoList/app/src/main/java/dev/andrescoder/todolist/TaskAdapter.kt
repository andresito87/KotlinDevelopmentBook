package dev.andrescoder.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.ArrayAdapter
import androidx.compose.material3.Button

class TaskAdapter(
    context: Context,
    private val tasks: MutableList<Task>,
    private val onDelete: (position: Int) -> Unit,
    private val onToggle: (position: Int) -> Unit
) : ArrayAdapter<Task>(context, 0, tasks) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_task, parent, false)

        val task = tasks[position]
        val desc = view.findViewById<TextView>(R.id.taskDesc)
        val delBtn = view.findViewById<Button>(R.id.deleteBtn)

        desc.text = task.description+ if (task.isCompleted) " [Done]" else ""

        desc.setOnClickListener { onToggle(position) }

        delBtn.setOnClickListener { onDelete(position) }

        return view
    }
}
