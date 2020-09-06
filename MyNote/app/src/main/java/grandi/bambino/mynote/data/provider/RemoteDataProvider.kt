package grandi.bambino.mynote.data.provider

import androidx.lifecycle.LiveData
import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.data.model.NoteResult
import grandi.bambino.mynote.data.model.User

interface RemoteDataProvider {
    fun subscribeToAllNotes(): LiveData<NoteResult>
    fun getNoteById(id: String): LiveData<NoteResult>
    fun saveNote(note: Note): LiveData<NoteResult>
    fun getCurrentUser(): LiveData<User?>
    fun deleteNote(noteId: String): LiveData<NoteResult>
}