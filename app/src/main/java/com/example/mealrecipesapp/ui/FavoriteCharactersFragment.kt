package com.example.mealrecipesapp.ui

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealrecipesapp.R
import com.example.mealrecipesapp.databinding.FragmentFavoriteCharactersBinding
import com.example.mealrecipesapp.repository.CharacterRepository
import com.example.mealrecipesapp.viewmodel.CharacterViewModel
import com.example.mealrecipesapp.viewmodel.CharacterViewModelFactory

class FavoriteCharactersFragment : Fragment() {

    private var _binding: FragmentFavoriteCharactersBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterViewModel
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Enable toolbar menu callbacks
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoriteCharactersBinding
        .inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Set up ViewModel (shared with list)
        val repo = CharacterRepository.getInstance(requireContext())
        val factory = CharacterViewModelFactory(repo)
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        // 2) Set up Adapter + RecyclerView
        adapter = CharacterAdapter { character ->
            findNavController().navigate(
                R.id.characterDetailsFragment,
                bundleOf("characterId" to character.id.toString())
            )
        }
        binding.rvFavorites.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteCharactersFragment.adapter
        }

        // 3) Observe favorites LiveData
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    // Inflate the same toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    // Handle toolbar item clicks here too
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_characters -> {
                // simply pop back to the character list
                findNavController().popBackStack()
                true
            }
            R.id.menu_favorites -> {
                // we're already on favorites
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
