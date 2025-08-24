package dev.andrescoder.postfeed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.andrescoder.postfeed.databinding.ItemPostBinding

/**
 * ListAdapter + DiffUtil para updates eficientes.
 * Soporta:
 *  - Click en item (onItemClick)
 *  - Payloads parciales: si solo cambia "content", hacemos update parcial.
 */
class PostAdapter(
    private val onItemClick: (Post) -> Unit
) : ListAdapter<Post, PostAdapter.PostViewHolder>(PostDiffCallback()) {

    inner class PostViewHolder(
        private val binding: ItemPostBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemRoot.setOnClickListener {
                val pos = bindingAdapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(pos))
                }
            }
        }

        fun bind(item: Post) {
            binding.title.text = item.title
            binding.content.text = item.content
        }

        fun updateContentOnly(newContent: String) {
            binding.content.text = newContent
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = ItemPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, /* attachToParent= */ false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty() && payloads.contains(PostDiffCallback.PAYLOAD_CONTENT_CHANGED)) {
            // Update parcial del contenido
            holder.updateContentOnly(getItem(position).content)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}