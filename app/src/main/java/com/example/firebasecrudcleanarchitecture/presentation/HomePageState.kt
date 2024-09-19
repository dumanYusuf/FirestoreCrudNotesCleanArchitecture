package com.example.firebasecrudcleanarchitecture.presentation

import com.example.firebasecrudcleanarchitecture.domain.model.Notes

data class HomePageState(
    val isLoading:Boolean=false,
    val errorMessage:String="",
    val noteList: List<Notes> = emptyList()
)
