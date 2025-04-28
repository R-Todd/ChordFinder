package com.example.rickopedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.rickopedia.databinding.FragmentCharacterDetailsBinding
import com.example.rickopedia.data.Character
import com.example.rickopedia.viewmodel.CharacterViewModel


class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private var characterId: String? = null
    private lateinit var viewModel: CharacterViewModel
    private var currentCharacter: Character? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characterId = arguments?.getString("characterId")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Get ViewModel the usual way
        viewModel = ViewModelProvider(requireActivity())
            .get(CharacterViewModel::class.java)

        // 2) Observe your character by ID (implement this in your ViewModel)
        characterId?.let { id ->
            viewModel.getCharacterById(id)
                .observe(viewLifecycleOwner) { character: Character? ->
                    if (character != null) {
                        currentCharacter = character
                        populateUI(character)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Character not found!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        // 3) Favorite button
        binding.btnFavorite.setOnClickListener {
            currentCharacter?.let { char ->
                viewModel.insertFavorite(char)
                Toast.makeText(
                    requireContext(),
                    "${char.name} added to favorites!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun populateUI(char: Character) {
        with(binding) {
            tvCharacterNameDetails.text = char.name
            ivCharacterImageDetails.load(char.image)
            tvCharacterStatusDetails.text = "Status: ${char.status}"
            tvCharacterSpeciesDetails.text = "Species: ${char.species}"
            tvCharacterGenderDetails.text = "Gender: ${char.gender}"
            tvCharacterOriginDetails.text = "Origin: ${char.origin.name}"
            tvCharacterLocationDetails.text = "Last Location: ${char.location.name}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
