package dev.andrescoder.socialfeedapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PostAdapter

    // Lista inmutable inicial
    private var posts: List<Post> = listOf(
        Post(
            id = 1,
            user = "Alice",
            content = "Enjoying the day!",
            likes = 10,
            imageUrl = "https://images.unsplash.com/photo-1500530855697-b586d89ba3ee",
            comments = listOf(
                Comment(11, "Bob", "Nice pic!"),
                Comment(12, "Sara", "Love it ❤️")
            )
        ),
        Post(
            id = 2,
            user = "Bob",
            content = "Just finished a run!",
            likes = 5,
            imageUrl = "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=1170&auto=format&fit=crop",
            comments = listOf(
                Comment(21, "Alice", "Great job!"),
                Comment(22, "Leo", "Keep going!")
            )
        ),
        Post(
            id = 3,
            user = "Sara",
            content = "Delicious homemade pizza!",
            likes = 8,
            imageUrl = "https://images.unsplash.com/photo-1542281286-9e0a16bb7366?q=80&w=1170&auto=format&fit=crop",
            comments = listOf(
                Comment(31, "Bob", "Yummy!"),
                Comment(32, "Alice", "Recipe please!")
            )
        ),
        Post(
            id = 4,
            user = "Mike",
            content = "Sunset at the beach.",
            likes = 15,
            imageUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=1170&auto=format&fit=crop",
            comments = listOf(
                Comment(41, "Sara", "Beautiful view!"),
                Comment(42, "Leo", "Wish I was there.")
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val feed = findViewById<RecyclerView>(R.id.feed)

        adapter = PostAdapter(
            onLike = { post ->
                // Crear nueva lista con el cambio
                posts = posts.map { if (it.id == post.id) it.copy(likes = it.likes + 1) else it }
                adapter.submitList(posts)   // ListAdapter hace el diff + animación
            },
            onItemClick = { post ->
                Toast.makeText(
                    this,
                    "Has pulsado: ${post.user} • ${post.content}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        feed.layoutManager = LinearLayoutManager(this)
        feed.setHasFixedSize(true)
        feed.adapter = adapter
        feed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        // Carga inicial
        adapter.submitList(posts)
    }
}
