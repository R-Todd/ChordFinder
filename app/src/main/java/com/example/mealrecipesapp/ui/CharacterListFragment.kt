package com.example.mealrecipesapp.ui

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealrecipesapp.R
import com.example.mealrecipesapp.databinding.FragmentCharacterListBinding
import com.example.mealrecipesapp.repository.CharacterRepository
import com.example.mealrecipesapp.viewmodel.CharacterViewModel
import com.example.mealrecipesapp.viewmodel.CharacterViewModelFactory

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
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
    ): View = FragmentCharacterListBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Set up ViewModel
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
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CharacterListFragment.adapter
        }

        // 3) Observe search results and restore query text if needed
        viewModel.searchResults.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
            if (viewModel.lastQuery.isNotEmpty()) {
                binding.etSearch.setText(viewModel.lastQuery)
            }
        }

        // 4) Wire up the search button
        binding.btnSearch.setOnClickListener {
            val q = binding.etSearch.text.toString().trim()
            if (q.isNotEmpty()) {
                viewModel.searchCharacters(q)
            }
        }
    }

    // 5) Inflate toolbar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    // 6) Handle toolbar item clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_favorites -> {
                findNavController().navigate(R.id.favoriteCharactersFragment)
                true
            }
            R.id.menu_characters -> {
                // we're already here
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
