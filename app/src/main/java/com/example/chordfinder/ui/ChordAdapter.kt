// File: ui/ChordAdapter.kt
// Functionality: Adapter to display chord details in a RecyclerView.
package com.example.chordfinder.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chordfinder.R
import com.example.chordfinder.data.model.Chord

// ChordAdapter extends RecyclerView.Adapter with a custom ViewHolder (ChordViewHolder)
// and holds a list of Chord objects to be displayed.
class ChordAdapter(private var chords: List<Chord>) : RecyclerView.Adapter<ChordAdapter.ChordViewHolder>() {

    // Inner class representing the ViewHolder for a single chord item.
    // It holds references to the views that display chord information.
    inner class ChordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TextView to display the chord's name.
        val chordNameTextView: TextView = itemView.findViewById(R.id.textViewChordName)
        // TextView to display the chord's notes.
        val chordNotesTextView: TextView = itemView.findViewById(R.id.textViewChordNotes)
    }

    // Called when RecyclerView needs a new ViewHolder of the given type.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChordViewHolder {
        // Inflate the layout defined in item_chord.xml for each item.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chord, parent, false)
        // Return a new instance of ChordViewHolder with the inflated view.
        return ChordViewHolder(view)
    }

    // Binds the data from a Chord object to the corresponding views in the ViewHolder.
    override fun onBindViewHolder(holder: ChordViewHolder, position: Int) {
        // Retrieve the chord item at the current position.
        val chord = chords[position]
        // Set the chord's name into the corresponding TextView.
        holder.chordNameTextView.text = chord.chordName
        // Set the chord's notes into the corresponding TextView.
        holder.chordNotesTextView.text = chord.chordNotes
    }

    // Returns the total number of chord items in the list.
    override fun getItemCount(): Int = chords.size

    // Updates the adapter's list of chords with new data and refreshes the RecyclerView.
    fun updateChords(newChords: List<Chord>) {
        // Replace the current list with the new list.
        chords = newChords
        // Notify the RecyclerView that the data set has changed to refresh the display.
        notifyDataSetChanged()
    }
}
