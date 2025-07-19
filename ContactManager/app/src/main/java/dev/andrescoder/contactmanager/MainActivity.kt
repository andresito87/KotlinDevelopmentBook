package dev.andrescoder.contactmanager

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private val contacts = mutableListOf<Contact>()
    private lateinit var adapter: ContactAdapter

    private val prefs by lazy {
        getSharedPreferences("todo_prefs", Context.MODE_PRIVATE)
    }
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val nameInput = findViewById<EditText>(R.id.nameInput)
        val phoneInput = findViewById<EditText>(R.id.phoneInput)
        val addButton = findViewById<Button>(R.id.addButton)
        val contactList = findViewById<ListView>(R.id.contactList)

        loadContacts()

        adapter = ContactAdapter(
            this,
            contacts,
            onDelete = { pos ->
                contacts.removeAt(pos)
                saveContacts()
                adapter.notifyDataSetChanged()
            },
            onCall = { pos ->
                contacts[pos].call()
            }
        )
        contactList.adapter = adapter

        addButton.setOnClickListener {
            val name = nameInput.text.toString().takeIf { it.isNotBlank() }
            val phone = phoneInput.text.toString().takeIf { it.isNotBlank() }
            if (name != null || phone != null) {
                contacts.add(Contact(name, phone))
                saveContacts()
                adapter.notifyDataSetChanged()
                nameInput.text.clear()
                phoneInput.text.clear()
            }
        }
    }

    private fun saveContacts() {
        val json = gson.toJson(contacts)
        prefs.edit().putString("contacts_json", json).apply()
    }

    private fun loadContacts() {
        prefs.getString("contacts_json", null)?.let { json ->
            val type = object : TypeToken<MutableList<Contact>>() {}.type
            val saved: MutableList<Contact> = gson.fromJson(json, type)
            contacts.clear()
            contacts.addAll(saved)
        }
    }
}
