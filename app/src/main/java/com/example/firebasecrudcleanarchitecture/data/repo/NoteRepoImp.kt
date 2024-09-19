package com.example.firebasecrudcleanarchitecture.data.repo


import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.domain.repo.NoteRepo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class NoteRepoImp @Inject constructor(private val firestore:FirebaseFirestore) :NoteRepo{

    private val notesCollection=firestore.collection("Notes")

    override suspend fun addNote(note: Notes) {
     //  notesCollection.document(note.id).set(note).await()
        // Firestore otomatik olarak bir belge ID'si oluşturur
        val newNoteRef = notesCollection.add(note).await()
        // ID'yi not ile güncelle
        val noteWithId = note.copy(id = newNoteRef.id)
        // Güncellenmiş notu Firestore'a kaydet
        notesCollection.document(noteWithId.id).set(noteWithId).await()
    }

    override suspend fun updateNote(note: Notes) {
       notesCollection.document(note.id).set(note).await()
    }

    override suspend fun deleteNote(note: Notes) {
        notesCollection.document(note.id).delete().await()
    }

    override fun getNote(): Flow<List<Notes>> = flow{
       val snapshot=notesCollection.get().await() // get verileri getiri anacak anlık olarak veri değişikliklerini dinlemez
        val notes=snapshot.documents.mapNotNull { it.toObject(Notes::class.java) }
        emit(notes)
    }

    /*
    // addSnapshotListener anlok olarak verileri dinler

    override fun getNote(): Flow<List<Notes>> = flow {
    notesCollection.addSnapshotListener { snapshot, error ->
        if (error != null || snapshot == null) return@addSnapshotListener
        val notes = snapshot.documents.mapNotNull { it.toObject(Notes::class.java) }
        emit(notes)
    }
}

     */


}