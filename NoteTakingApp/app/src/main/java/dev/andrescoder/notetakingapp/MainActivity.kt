package dev.andrescoder.notetakingapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var noteDao: NoteDao
    private lateinit var adapter: NotesAdapter

    // Nota actualmente en edición (null = modo insertar)
    private var editingNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "notes_db"
        ).build()

        noteDao = db.noteDao()

        val titleInput = findViewById<EditText>(R.id.titleInput)
        val contentInput = findViewById<EditText>(R.id.contentInput)
        val saveButton = findViewById<Button>(R.id.saveButton)
        val noteList = findViewById<ListView>(R.id.noteList)

        adapter = NotesAdapter(
            context = this,
            items = mutableListOf(),
            onEditClick = { note ->
                // Entrar en modo edición
                editingNote = note
                titleInput.setText(note.title)
                contentInput.setText(note.content)
                saveButton.text = "Update"
            }
        )
        noteList.adapter = adapter

        // Guardar / Actualizar
        saveButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val content = contentInput.text.toString().trim()
            if (title.isEmpty() || content.isEmpty()) return@setOnClickListener

            lifecycleScope.launch(Dispatchers.IO) {
                val current = editingNote
                if (current == null) {
                    // Insertar
                    noteDao.insert(Note(title = title, content = content))
                } else {
                    // Actualizar
                    noteDao.update(current.copy(title = title, content = content))
                    editingNote = null
                }
                updateNotes()
                withContext(Dispatchers.Main) {
                    titleInput.text.clear()
                    contentInput.text.clear()
                    saveButton.text = "Save"
                }
            }
        }

        // Borrar con long click
        noteList.setOnItemLongClickListener { _, _, position, _ ->
            val note = adapter.getItem(position)
            if (note != null) {
                lifecycleScope.launch(Dispatchers.IO) {
                    noteDao.delete(note)
                    updateNotes()
                }
            }
            true
        }

        // Carga inicial
        updateNotes()
    }

    private fun updateNotes() {
        lifecycleScope.launch(Dispatchers.IO) {
            val notes = noteDao.getAll()
            withContext(Dispatchers.Main) {
                adapter.updateData(notes)
            }
        }
    }
}
