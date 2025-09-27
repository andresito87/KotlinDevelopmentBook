package dev.andrescoder.notetakingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class NotesAdapter(
    context: Context,
    private val items: MutableList<Note>,
    private val onEditClick: (Note) -> Unit,
) : ArrayAdapter<Note>(context, 0, items) {

    fun updateData(newList: List<Note>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.row_note, parent, false)

        val note = getItem(position)!!
        val tvSummary = view.findViewById<TextView>(R.id.tvSummary)
        val btnEdit = view.findViewById<Button>(R.id.btnEdit)

        tvSummary.text = "${note.title}: ${note.content.take(20)}..."
        btnEdit.setOnClickListener { onEditClick(note) }

        return view
    }
}
