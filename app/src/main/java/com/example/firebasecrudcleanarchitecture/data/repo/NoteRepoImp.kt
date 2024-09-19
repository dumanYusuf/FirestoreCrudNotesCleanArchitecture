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
        val newNoteRef = notesCollection.add(note).await()
        val noteWithId = note.copy(id = newNoteRef.id)
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

    override fun searchNote(search:String): Flow<List<Notes>> = flow {
        val snapshot=notesCollection.get().await()// burda tüm verileri alıyoruz
        val searchList=snapshot.documents.mapNotNull { it.toObject(Notes::class.java) }// burda da aldığımız belgeleri Notes nesnesine şeviriyoruz null verileer hariç
        val filterNote=searchList.filter {note->
            note.title.contains(search, ignoreCase = true)
        }
        emit(filterNote)
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