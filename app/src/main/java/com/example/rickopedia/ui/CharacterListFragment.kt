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
import com.example.rickopedia.databinding.FragmentCharacterListBinding
import com.example.rickopedia.viewmodel.CharacterViewModel
import com.example.rickopedia.viewmodel.CharacterViewModelFactory

class CharacterListFragment : Fragment() {

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterViewModel
    private lateinit var adapter: CharacterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentCharacterListBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) set up VM with our factory
        val factory = CharacterViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        // 2) set up the adapter
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

        // 3) observe results
        viewModel.searchResults.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }

        // 4) wire up search button
        binding.btnSearch.setOnClickListener {
            val q = binding.etSearch.text.toString().trim()
            if (q.isNotEmpty()) viewModel.searchCharacters(q)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
