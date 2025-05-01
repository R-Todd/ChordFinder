package com.example.rickopedia.ui

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickopedia.R
import com.example.rickopedia.databinding.FragmentCharacterListBinding
import com.example.rickopedia.viewmodel.CharacterViewModel
import com.example.rickopedia.viewmodel.CharacterViewModelFactory

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterViewModel
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) ViewModel + Factory
        val factory = CharacterViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        // 2) Adapter + click -> details
        adapter = CharacterAdapter { character ->
            val args = bundleOf("characterId" to character.id)
            findNavController().navigate(
                R.id.characterDetailsFragment,
                args
            )
        }
        binding.rvCharacters.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CharacterListFragment.adapter
        }

        // 3) Observe
        viewModel.searchResults.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // 4) Search button in fragment layout
        binding.btnSearch.setOnClickListener {
            val q = binding.etSearch.text.toString().trim()
            if (q.isNotEmpty()) viewModel.searchCharacters(q)
        }
    }

    // Tell Fragment it has its own menu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    // Inflate our menu_main.xml into the Toolbar
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    // Handle the Favorites button tap
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_favorites -> {
                findNavController().navigate(R.id.favoriteCharactersFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
