package com.example.firebasecrudcleanarchitecture.data.di

import com.example.firebasecrudcleanarchitecture.data.repo.NoteRepoImp
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModulle {

    @Provides
    @Singleton
    fun provideFirestore():FirebaseFirestore=FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideNotesRepo(firestore:FirebaseFirestore):NoteRepo{
        return NoteRepoImp(firestore)
    }

}