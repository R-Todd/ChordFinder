package com.example.rickopedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickopedia.R
import com.example.rickopedia.data.Character
import com.example.rickopedia.databinding.FragmentCharacterDetailsBinding
import com.example.rickopedia.viewmodel.CharacterViewModel
import com.example.rickopedia.viewmodel.CharacterViewModelFactory
import android.widget.Toast
import coil.load

class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterViewModel
    private var characterId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // pull your nav-arg (make sure in nav_graph.xml it's defined with app:argType="integer")
        characterId = arguments?.getInt("characterId") ?: -1
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

        // 1) Get your ViewModel via the Factory (so repo is injected)
        val factory = CharacterViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        // 2) Kick off load if we have a valid ID
        if (characterId != -1) {
            viewModel.getCharacterById(characterId) { character ->
                // This callback is on a background thread, so switch back to main:
                requireActivity().runOnUiThread {
                    if (character != null) {
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
        }
    }

    private fun populateUI(character: Character) {
        // bind all your views from fragment_character_details.xml
        binding.tvCharacterNameDetails.text    = character.name
        binding.ivCharacterImageDetails.load(character.image)

        binding.tvCharacterStatusDetails.text   = "Status: ${character.status}"
        binding.tvCharacterSpeciesDetails.text  = "Species: ${character.species}"
        binding.tvCharacterGenderDetails.text   = "Gender: ${character.gender}"
        binding.tvCharacterOriginDetails.text   = "Origin: ${character.origin.name}"
        binding.tvCharacterLocationDetails.text = "Last Seen: ${character.location.name}"

        binding.btnFavorite.setOnClickListener {
            viewModel.insertFavorite(character)
            Toast.makeText(requireContext(), "Added to favorites", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
