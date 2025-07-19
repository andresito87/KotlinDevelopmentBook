package dev.andrescoder.contactmanager

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class ContactAdapter(
    context: Context,
    private val contacts: MutableList<Contact>,
    private val onDelete: (Int) -> Unit,
    private val onCall: (Int) -> Unit
) : ArrayAdapter<Contact>(context, 0, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = contacts[position]
        val desc = view.findViewById<TextView>(R.id.contactDesc)
        val delBtn = view.findViewById<Button>(R.id.deleteBtn)

        desc.text = contact.display()
        desc.setOnClickListener { onCall(position) }
        delBtn.setOnClickListener { onDelete(position) }

        return view
    }
}
