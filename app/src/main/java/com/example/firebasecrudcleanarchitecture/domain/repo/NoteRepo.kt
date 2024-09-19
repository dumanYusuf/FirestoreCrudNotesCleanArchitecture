package com.example.firebasecrudcleanarchitecture.domain.repo

import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import kotlinx.coroutines.flow.Flow


interface NoteRepo {

    suspend fun addNote(note: Notes)
    suspend fun updateNote(note:Notes)
    suspend fun deleteNote(note:Notes)
    fun getNote():Flow<List<Notes>>
    fun searchNote(search:String):Flow<List<Notes>>
}