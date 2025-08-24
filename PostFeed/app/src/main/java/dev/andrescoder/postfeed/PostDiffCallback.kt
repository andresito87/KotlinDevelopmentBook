package dev.andrescoder.postfeed

import androidx.recyclerview.widget.DiffUtil

/**
 * DiffUtil define cómo comparar elementos para animar/actualizar eficientemente.
 * También devolvemos un "payload" cuando sólo cambia el contenido.
 */
class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem

    override fun getChangePayload(oldItem: Post, newItem: Post): Any? {
        return if (oldItem.title == newItem.title && oldItem.content != newItem.content) {
            // Indicamos que solo cambió el "content"
            PAYLOAD_CONTENT_CHANGED
        } else {
            null
        }
    }

    companion object {
        const val PAYLOAD_CONTENT_CHANGED = "payload_content_changed"
    }
}