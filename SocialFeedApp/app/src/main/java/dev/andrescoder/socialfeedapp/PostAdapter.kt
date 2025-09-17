package dev.andrescoder.socialfeedapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PostAdapter(
    private val onLike: (Post) -> Unit,
    private val onItemClick: (Post) -> Unit = {},
) : ListAdapter<Post, PostViewHolder>(PostDiff) {

    // Pool compartido para los RecyclerViews de comentarios
    private val sharedPool = RecyclerView.RecycledViewPool()

    // Estado UI: qué posts tienen comentarios visibles
    private val expanded = mutableSetOf<Long>()

    init { setHasStableIds(true) }

    override fun getItemId(position: Int): Long = getItem(position).id.toLong()

    private fun indexOfId(id: Long): Int =
        currentList.indexOfFirst { it.id.toLong() == id }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post, parent, false)

        return PostViewHolder(
            itemView = view,
            sharedPool = sharedPool,
            onItemClick = onItemClick,
            onLikeClick = onLike,
            onToggleComments = { post ->
                val key = post.id.toLong()
                if (!expanded.add(key)) expanded.remove(key)
                indexOfId(key).takeIf { it != -1 }?.let { notifyItemChanged(it) }
            }
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, expanded.contains(item.id.toLong()))
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.contains(PostDiff.PAYLOAD_LIKES)) {
            holder.bindLikesOnly(getItem(position).likes)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}
