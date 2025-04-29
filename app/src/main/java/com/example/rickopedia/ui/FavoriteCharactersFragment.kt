package com.example.rickopedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickopedia.R
import com.example.rickopedia.databinding.FragmentFavoriteCharactersBinding
import com.example.rickopedia.viewmodel.CharacterViewModel
import com.example.rickopedia.viewmodel.CharacterViewModelFactory

class FavoriteCharactersFragment : Fragment() {

    private var _binding: FragmentFavoriteCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterViewModel
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentFavoriteCharactersBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) VM
        val factory = CharacterViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        // 2) Adapter
        adapter = CharacterAdapter { character ->
            val args = bundleOf("characterId" to character.id)
            findNavController().navigate(
                R.id.characterDetailsFragment,
                args
            )
        }

        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteCharactersFragment.adapter
        }

        // 3) Observe favorites
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
