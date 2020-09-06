package grandi.bambino.mynote.ui.main

import androidx.annotation.VisibleForTesting

import androidx.lifecycle.Observer
import grandi.bambino.mynote.data.Repository


import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.data.model.NoteResult

import grandi.bambino.mynote.ui.base.BaseViewModel


class MainViewModel(private val notesRepository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = object : Observer<NoteResult>{
        override fun onChanged(t: NoteResult?) {
            t ?: return

            when(t){
                is NoteResult.Success<*> -> {
                    viewStateLiveData.value = MainViewState(notes = t.data as? List<Note>)
                }
                is NoteResult.Error -> {
                    viewStateLiveData.value = MainViewState(error = t.error)
                }
            }
        }
    }

    private val repositoryNotes = notesRepository.getNotes()

    init {
        viewStateLiveData.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }

    @VisibleForTesting
    override public fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }

}

