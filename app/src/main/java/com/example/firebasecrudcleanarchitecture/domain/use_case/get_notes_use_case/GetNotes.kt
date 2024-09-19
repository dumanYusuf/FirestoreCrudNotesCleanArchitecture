package com.example.firebasecrudcleanarchitecture.domain.use_case.get_notes_use_case

import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetNotes @Inject constructor(private val repo: NoteRepo){

     fun invoke(): Flow<List<Notes>>{
         return repo.getNote()
     }

}