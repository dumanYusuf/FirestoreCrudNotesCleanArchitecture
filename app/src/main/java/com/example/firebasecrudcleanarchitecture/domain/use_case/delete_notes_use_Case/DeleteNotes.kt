package com.example.firebasecrudcleanarchitecture.domain.use_case.delete_notes_use_Case

import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import javax.inject.Inject

class DeleteNotes @Inject constructor(private val repo: NoteRepo) {


    suspend operator fun invoke(notes: Notes){
        repo.deleteNote(notes)
    }

}