package com.example.rickopedia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickopedia.R
import com.example.rickopedia.data.Character
import com.example.rickopedia.databinding.ItemCharacterBinding
import android.view.View

private fun statusColor(status: String): Int = when (status.lowercase()) {
    "alive"  -> R.color.badge_alive
    "dead"   -> R.color.badge_dead
    else     -> R.color.badge_unknown
}
private fun statusLabel(status: String): String =
    status.replaceFirstChar { it.uppercase() }


class CharacterAdapter(
    private val onClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val items = mutableListOf<Character>()
    private var lastAnimated = -1

    fun submitList(newList: List<Character>) {
        items.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder =
        CharacterViewHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])

        /* slide-in on first appearance */
        if (position > lastAnimated) {
            holder.itemView.startAnimation(
                AnimationUtils.loadAnimation(holder.itemView.context, R.anim.row_slide_in)
            )
            lastAnimated = position
        }
    }

    override fun getItemCount(): Int = items.size


    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {

            binding.shimmerThumb.startShimmer()
            binding.shimmerThumb.visibility = View.VISIBLE

            binding.ivCharacterThumb.load(character.image) {
                crossfade(true)
                listener(
                    onSuccess = { _, _ ->
                        binding.shimmerThumb.stopShimmer()
                        binding.shimmerThumb.visibility = View.GONE
                    },
                    onError = { _, _ ->
                        binding.shimmerThumb.stopShimmer()
                        binding.shimmerThumb.visibility = View.GONE
                    }
                )
            }

            binding.tvCharacterName.text = character.name
            binding.chipStatus.apply {
                text = statusLabel(character.status)
                setChipBackgroundColorResource(statusColor(character.status))
            }

            itemView.setOnClickListener {
                itemView.postDelayed({ onClick(character) }, 120)
            }
        }
    }
}
