package com.example.firebasecrudcleanarchitecture.domain.use_case.search_note_use_case

import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class SearchNotes @Inject constructor(private val repo: NoteRepo){

    fun invoke(search:String):Flow<List<Notes>>{
        return repo.searchNote(search)
    }

}