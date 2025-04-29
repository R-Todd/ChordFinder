package com.example.rickopedia.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.rickopedia.data.Character
import com.example.rickopedia.databinding.ItemCharacterBinding

/**
 * Adapter for both the search results and the favorites list.
 * Make sure your item_character.xml has these IDs:
 *   - ImageView  → @+id/ivCharacterThumb
 *   - TextView   → @+id/tvCharacterName
 */
class CharacterAdapter(
    private val onClick: (Character) -> Unit
) : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val items = mutableListOf<Character>()

    /** Replace the list and refresh */
    fun submitList(newList: List<Character>) {
        items.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class CharacterViewHolder(
        private val binding: ItemCharacterBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            // load into ivCharacterThumb, not ivCharacterImage!
            binding.ivCharacterThumb.load(character.image)
            binding.tvCharacterName.text = character.name

            binding.root.setOnClickListener {
                onClick(character)
            }
        }
    }
}
