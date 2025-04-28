package com.example.mealrecipesapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.mealrecipesapp.data.Character
import com.example.mealrecipesapp.databinding.ItemCharacterBinding
import com.example.mealrecipesapp.viewmodel.CharacterViewModelFactory


class CharacterAdapter(
    private val onClick: (Character) -> Unit
) : ListAdapter<Character, CharacterAdapter.VH>(Diff()) {

    class VH(private val b: ItemCharacterBinding)
        : RecyclerView.ViewHolder(b.root) {
        fun bind(c: Character, onClick: (Character) -> Unit) = with(b) {
            tvCharacterName.text = c.name
            Glide.with(root).load(c.image).into(ivCharacterThumb)
            root.setOnClickListener { onClick(c) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))

    override fun onBindViewHolder(holder: VH, pos: Int) {
        holder.bind(getItem(pos), onClick)
    }

    private class Diff : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(a: Character, b: Character) = a.id == b.id
        override fun areContentsTheSame(a: Character, b: Character) = a == b
    }
}
