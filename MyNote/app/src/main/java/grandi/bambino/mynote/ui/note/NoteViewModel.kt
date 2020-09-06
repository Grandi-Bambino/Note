package grandi.bambino.mynote.ui.note


import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer

import grandi.bambino.mynote.data.Repository
import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.data.model.NoteResult

import grandi.bambino.mynote.ui.base.BaseViewModel


class NoteViewModel(private val notesRepository: Repository) : BaseViewModel<NoteViewState.Data, NoteViewState>() {

    private val pendingNote: Note?
        get() = viewStateLiveData.value?.data?.note

    fun save(note: Note) {
        viewStateLiveData.value = NoteViewState(NoteViewState.Data(note = note))
    }

    fun loadNote(noteId: String) {
        notesRepository.getNoteById(noteId).observeForever { result ->
            result?.let {
                viewStateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(note = result.data as Note))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            }
        }
    }

    fun deleteNote() {
        pendingNote?.let {
            notesRepository.deleteNote(it.id).observeForever { result ->
                viewStateLiveData.value = when (result) {
                    is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                    is NoteResult.Error -> NoteViewState(error = result.error)
                }
            }
        }
    }

    @VisibleForTesting
    override public fun onCleared() {
        pendingNote?.let {
            notesRepository.saveNote(it)
        }
    }
}
