package com.example.rickopedia.ui

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
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

        /* 1) ViewModel */
        val factory = CharacterViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        /* 2) RecyclerView + Adapter */
        adapter = CharacterAdapter { character ->
            val args = bundleOf("characterId" to character.id)
            findNavController().navigate(R.id.characterDetailsFragment, args)
        }
        binding.rvCharacters.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCharacters.adapter = adapter

        /* 3) LiveData observation */
        viewModel.searchResults.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        /* 4) Explicit Search button */
        binding.btnSearch.setOnClickListener { launchSearch() }

        /* 5) IME actionSearch (“enter” on keyboard) */
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                launchSearch()
                true
            } else false
        }
    }

    /** Runs a search and hides the soft-keyboard */
    private fun launchSearch() {
        val query = binding.etSearch.text?.toString()?.trim().orEmpty()
        if (query.isNotEmpty()) {
            viewModel.searchCharacters(query)
            // hide keyboard
            requireContext()
                .getSystemService<InputMethodManager>()
                ?.hideSoftInputFromWindow(binding.etSearch.windowToken, 0)
        }
    }

    /* ==== Toolbar menu ==== */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        if (item.itemId == R.id.menu_favorites) {
            findNavController().navigate(R.id.favoriteCharactersFragment)
            true
        } else super.onOptionsItemSelected(item)

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
