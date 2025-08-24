package dev.andrescoder.shoppinglistapp

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<Item>()
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var categorySpinner: Spinner
    private lateinit var clearButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<View>(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val itemInput = findViewById<EditText>(R.id.itemInput)
        val addButton = findViewById<Button>(R.id.addButton)
        val categories = listOf("General", "Fruits", "Vegetables", "Drinks", "Cleaning stuff", "Snacks", "Dairy", "Meat", "Bakery", "Frozen", "Condiments")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)

        val itemList = findViewById<ListView>(R.id.itemList)
        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            mutableListOf()
        )

        categorySpinner = findViewById(R.id.categorySpinner)
        clearButton = findViewById(R.id.clearButton)

        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = spinnerAdapter

        itemList.adapter = adapter
        addButton.setOnClickListener {
            itemInput.text.toString().takeIf { it.isNotBlank() }
                ?.let { name ->
                    val existingItem =
                        items.find { it.name == name }
                    if (existingItem != null) {
                        items[items.indexOf(existingItem)] =
                            existingItem.copy(quantity = existingItem.quantity + 1)
                    } else {
                        val selectedCategory = categorySpinner.selectedItem.toString()
                        items.add(Item(name, selectedCategory))
                    }
                    updateList()
                    itemInput.text.clear()
                }
        }

        itemList.setOnItemLongClickListener { _, _, position, _ ->
            items.removeAt(position)
            updateList()
            true
        }

        clearButton.setOnClickListener {
            items.clear()
            updateList()
        }
    }

    private fun updateList() {
        adapter.clear()
        items.map { "${it.name} (x${it.quantity}) - Cat: ${it.category}" }
            .let { adapter.addAll(it) }
        adapter.notifyDataSetChanged()

    }
}