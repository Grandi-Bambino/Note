package grandi.bambino.mynote.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import grandi.bambino.mynote.R
import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.getColorInt
import kotlinx.android.synthetic.main.item_notes.view.*

class RecyclerViewAdapter(val onItemViewClick : ((note: Note) -> Unit)? = null) : RecyclerView.Adapter<RecyclerViewAdapter.NoteViewHolder>(){

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        NoteViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_notes, parent, false)
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(vh: NoteViewHolder, pos: Int) = vh.bind(notes[pos])

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.text
            (this as CardView).setCardBackgroundColor(note.color.getColorInt(context))
            itemView.setOnClickListener {
                onItemViewClick?.invoke(note)
            }

        }
    }
}