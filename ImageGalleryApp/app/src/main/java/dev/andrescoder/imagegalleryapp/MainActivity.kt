package dev.andrescoder.imagegalleryapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    val images = listOf(
        Image(1, "https://picsum.photos/seed/1/600/600"),
        Image(2, "https://picsum.photos/seed/2/600/600"),
        Image(3, "https://picsum.photos/seed/3/600/600"),
        Image(4, "https://picsum.photos/seed/4/600/600"),
        Image(5, "https://picsum.photos/seed/5/600/600"),
        Image(6, "https://picsum.photos/seed/6/600/600"),
        Image(7, "https://picsum.photos/seed/7/600/600"),
        Image(8, "https://picsum.photos/seed/8/600/600"),
        Image(9, "https://picsum.photos/seed/9/600/600"),
        Image(10, "https://picsum.photos/seed/10/600/600"),
        Image(11, "https://picsum.photos/seed/11/600/600"),
        Image(12, "https://picsum.photos/seed/12/600/600"),
        Image(13, "https://picsum.photos/seed/13/600/600"),
        Image(14, "https://picsum.photos/seed/14/600/600"),
        Image(15, "https://picsum.photos/seed/15/600/600"),
        Image(16, "https://picsum.photos/seed/16/600/600"),
        Image(17, "https://picsum.photos/seed/17/600/600"),
        Image(18, "https://picsum.photos/seed/18/600/600"),
        Image(19, "https://picsum.photos/seed/19/600/600"),
        Image(20, "https://picsum.photos/seed/20/600/600"),
        Image(21, "https://picsum.photos/seed/21/600/600"),
        Image(22, "https://picsum.photos/seed/22/600/600"),
        Image(23, "https://picsum.photos/seed/23/600/600"),
        Image(24, "https://picsum.photos/seed/24/600/600"),
        Image(25, "https://picsum.photos/seed/25/600/600"),
        Image(26, "https://picsum.photos/seed/26/600/600"),
        Image(27, "https://picsum.photos/seed/27/600/600"),
        Image(28, "https://picsum.photos/seed/28/600/600"),
        Image(29, "https://picsum.photos/seed/29/600/600"),
        Image(30, "https://picsum.photos/seed/30/600/600"),
        Image(31, "https://picsum.photos/seed/31/600/600"),
        Image(32, "https://picsum.photos/seed/32/600/600"),
        Image(33, "https://picsum.photos/seed/33/600/600"),
        Image(34, "https://picsum.photos/seed/34/600/600"),
        Image(35, "https://picsum.photos/seed/35/600/600"),
        Image(36, "https://picsum.photos/seed/36/600/600"),
        Image(37, "https://picsum.photos/seed/37/600/600"),
        Image(38, "https://picsum.photos/seed/38/600/600"),
        Image(39, "https://picsum.photos/seed/39/600/600"),
        Image(40, "https://picsum.photos/seed/40/600/600"),
        Image(41, "https://picsum.photos/seed/41/600/600"),
        Image(42, "https://picsum.photos/seed/42/600/600"),
        Image(43, "https://picsum.photos/seed/43/600/600"),
        Image(44, "https://picsum.photos/seed/44/600/600"),
        Image(45, "https://picsum.photos/seed/45/600/600"),
        Image(46, "https://picsum.photos/seed/46/600/600"),
        Image(47, "https://picsum.photos/seed/47/600/600"),
        Image(48, "https://picsum.photos/seed/48/600/600"),
        Image(49, "https://picsum.photos/seed/49/600/600"),
        Image(50, "https://picsum.photos/seed/50/600/600"),
        Image(51, "https://picsum.photos/seed/54/600/600")
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView = findViewById<RecyclerView>(R.id.gallery)
        val adapter = ImageAdapter(images) { image ->
            Toast.makeText(
                this,
                "Clicked image ${image.id}",
                Toast.LENGTH_SHORT
            ).show()
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)
    }
}
