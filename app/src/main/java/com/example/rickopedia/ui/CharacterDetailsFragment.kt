package com.example.rickopedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.rickopedia.R
import com.example.rickopedia.data.Character
import com.example.rickopedia.databinding.FragmentCharacterDetailsBinding
import com.example.rickopedia.viewmodel.CharacterViewModel
import com.example.rickopedia.viewmodel.CharacterViewModelFactory
import coil.load

class CharacterDetailsFragment : Fragment() {

    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CharacterViewModel
    private var characterId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Pull your nav-arg (must be defined as <argument name="characterId" app:argType="integer"/> in nav_graph.xml)
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

        // 1) Obtain the ViewModel via our Factory (so the repo is injected)
        val factory = CharacterViewModelFactory(requireContext())
        viewModel = ViewModelProvider(requireActivity(), factory)
            .get(CharacterViewModel::class.java)

        // 2) Kick off load if we have a valid ID
        if (characterId != -1) {
            viewModel.getCharacterById(characterId) { character ->
                // Our onResult callback runs on a background thread,
                // so hop back to the main thread before touching UI:
                requireActivity().runOnUiThread {
                    if (character != null) {
                        populateUI(character)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.character_not_found),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        } else {
            Toast.makeText(
                requireContext(),
                getString(R.string.invalid_character_id),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun populateUI(character: Character) {
        binding.tvCharacterNameDetails.text    = character.name
        binding.ivCharacterImageDetails.load(character.image)

        binding.tvCharacterStatusDetails.text   = getString(R.string.status_label, character.status)
        binding.tvCharacterSpeciesDetails.text  = getString(R.string.species_label, character.species)
        binding.tvCharacterGenderDetails.text   = getString(R.string.gender_label, character.gender)
        binding.tvCharacterOriginDetails.text   = getString(R.string.origin_label, character.origin.name)
        binding.tvCharacterLocationDetails.text = getString(R.string.location_label, character.location.name)

        binding.btnFavorite.setOnClickListener {
            viewModel.insertFavorite(character)
            Toast.makeText(requireContext(), getString(R.string.added_to_favorites), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
