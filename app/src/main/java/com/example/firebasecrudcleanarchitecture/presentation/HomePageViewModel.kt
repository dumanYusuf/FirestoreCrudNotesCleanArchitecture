package com.example.firebasecrudcleanarchitecture.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.use_case.add_notes_use_case.AddNote
import com.example.firebasecrudcleanarchitecture.domain.use_case.delete_notes_use_Case.DeleteNotes
import com.example.firebasecrudcleanarchitecture.domain.use_case.get_notes_use_case.GetNotes
import com.example.firebasecrudcleanarchitecture.domain.use_case.search_note_use_case.SearchNotes
import com.example.firebasecrudcleanarchitecture.domain.use_case.update_notes_use_case.UpdateNotes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val getNotesusecase:GetNotes,
    private val addNotesUseCase:AddNote,
    private val deleteNotesUseCase: DeleteNotes,
    private val updateNotesUsecase:UpdateNotes,
    private val searchNotesUseCase: SearchNotes
) :ViewModel(){

 private val _notes = MutableStateFlow<List<Notes>>(emptyList())
    val stateNotes: StateFlow<List<Notes>> = _notes.asStateFlow()




    fun addNote(notes: Notes){
        viewModelScope.launch {
            addNotesUseCase.invoke(notes)
            Log.e("save","success")
        }
    }

    fun getNotes()=viewModelScope.launch {
        getNotesusecase.invoke().collect{result->
            _notes.value=result
            Log.e("getNotes","succsess getNotes")
            println("${_notes.value}")
        }
    }

    fun deleteNotes(notes: Notes)=viewModelScope.launch {
        deleteNotesUseCase.invoke(notes)
    }

    fun updateNotes(notes: Notes){
        viewModelScope.launch {
            updateNotesUsecase.invoke(notes)
        }
    }

    fun searchNotes(search:String){
        viewModelScope.launch {
            searchNotesUseCase.invoke(search).collect{searchNotes->
                _notes.value=searchNotes
                Log.e("searchNotes","succsess searchNotes")
                println("${_notes.value}")
            }
        }
    }

}