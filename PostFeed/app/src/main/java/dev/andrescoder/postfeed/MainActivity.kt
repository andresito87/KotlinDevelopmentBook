package dev.andrescoder.postfeed

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dev.andrescoder.postfeed.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val adapter by lazy {
        PostAdapter { post ->
            Toast.makeText(this, "Click: ${post.title}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // ViewBinding del layout de la Activity
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Aplica los insets al "root" real que estás mostrando
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar RecyclerView
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = this@MainActivity.adapter
        }

        // Datos iniciales
        val initial = listOf(
            Post(1, "First Post", "Hello, world!"),
            Post(2, "Second Post", "Android is fun!"),
            Post(3, "Third Post", "RecyclerView + DiffUtil + ViewBinding")
        )
        adapter.submitList(initial)

        // Simula update parcial tras 2.5s
        Handler(Looper.getMainLooper()).postDelayed({
            val updated = listOf(
                Post(1, "First Post", "Hello, world!"),
                Post(2, "Second Post", "Content UPDATED via Handler with payload ✔"),
                Post(3, "Third Post", "RecyclerView + DiffUtil + ViewBinding")
            )
            adapter.submitList(updated)
        }, 2500)
    }
}
