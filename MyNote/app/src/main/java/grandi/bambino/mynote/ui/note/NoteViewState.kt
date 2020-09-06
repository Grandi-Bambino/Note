package grandi.bambino.mynote.ui.note

import grandi.bambino.mynote.data.model.Note
import grandi.bambino.mynote.ui.base.BaseViewState
import java.lang.Error

class NoteViewState(data: Data = Data(), error: Throwable? = null) : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}