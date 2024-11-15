package com.moondroid.compose.mvi.ui.features.note

import com.moondroid.compose.mvi.common.BoxColor
import com.moondroid.compose.mvi.domain.model.Note

class NoteContract {
    sealed interface State {
        data object Loading : State
        data class Success(val note: Note) : State
        data class Error(val message: String) : State
    }

    sealed interface Intent {
        data class LoadNote(val id: Int) : Intent
        data class ChangeContent(val description: String) : Intent
        data class ChangeBoxColor(val boxColor: BoxColor) : Intent
        data object Save : Intent
    }

    sealed interface Effect {
        data object Done : Effect
    }
}

inline fun NoteContract.State.onLoading(action: () -> Unit) : NoteContract.State {
    if (this is NoteContract.State.Loading) action()
    return this
}

inline fun NoteContract.State.onSuccess(action: (Note) -> Unit) : NoteContract.State {
    if (this is NoteContract.State.Success) action(note)
    return this
}

inline fun NoteContract.State.onError(action: (String) -> Unit) : NoteContract.State {
    if (this is NoteContract.State.Error) action(message)
    return this
}