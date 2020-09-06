package grandi.bambino.mynote.ui.main

import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.ui.base.BaseViewState

class MainViewState (val notes: List<Note>? = null, error: Throwable? = null)
    : BaseViewState<List<Note>?>(notes, error) //класс для передачи данных из ViewModel во view

