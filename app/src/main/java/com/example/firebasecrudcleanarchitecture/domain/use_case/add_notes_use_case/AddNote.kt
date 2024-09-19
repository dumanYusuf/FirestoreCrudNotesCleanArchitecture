package com.example.firebasecrudcleanarchitecture.domain.use_case.add_notes_use_case

import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import javax.inject.Inject

class AddNote@Inject constructor(private val repo:NoteRepo) {

    suspend operator fun invoke(notes: Notes){
        repo.addNote(notes)
    }

}