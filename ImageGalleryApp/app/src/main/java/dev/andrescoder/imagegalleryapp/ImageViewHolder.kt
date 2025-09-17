package dev.andrescoder.imagegalleryapp

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image: ImageView = itemView.findViewById(R.id.image)
    fun bind(image: Image) {
        Glide.with(itemView.context).load(image.url).into(this.image)
    }
}
