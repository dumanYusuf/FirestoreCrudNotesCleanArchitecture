package com.example.firebasecrudcleanarchitecture.domain.use_case.update_notes_use_case

import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import javax.inject.Inject

class UpdateNotes @Inject constructor(private val repo: NoteRepo) {

    suspend operator fun invoke(notes: Notes){
         repo.updateNote(notes)
    }

}