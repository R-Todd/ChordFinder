// File: ui/ChordSearchFragment.kt
// Functionality: Provides a UI to input a chord name, perform a search, and display the chord details in a RecyclerView.
package com.example.chordfinder.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.chordfinder.R
import com.example.chordfinder.data.model.Chord

class ChordSearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editTextChordName = view.findViewById<EditText>(R.id.editTextChordName)
        val buttonSearch = view.findViewById<Button>(R.id.buttonSearch)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewChords)

        // Set up RecyclerView with a LinearLayoutManager and the adapter
        val adapter = ChordAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // On clicking the search button, display a dummy chord result in the RecyclerView.
        buttonSearch.setOnClickListener {
            val chordName = editTextChordName.text.toString()
            if (chordName.isNotBlank()) {
                // Create a dummy chord result for demonstration
                val dummyChord = Chord(chordName, "Notes for $chordName", "Fingering for $chordName")
                adapter.updateChords(listOf(dummyChord))
                Toast.makeText(requireContext(), "Displaying chord: $chordName", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Please enter a chord name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
