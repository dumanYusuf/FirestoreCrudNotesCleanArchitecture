package com.example.firebasecrudcleanarchitecture.presentation.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.firebasecrudcleanarchitecture.R
import com.example.firebasecrudcleanarchitecture.domain.model.Notes
import com.example.firebasecrudcleanarchitecture.presentation.HomePageViewModel




@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    viewModel: HomePageViewModel = hiltViewModel()
) {
    val notesList by viewModel.stateNotes.collectAsState()
    val showDialog = remember { mutableStateOf(false) }
    val noteToEdit = remember { mutableStateOf<Notes?>(null) }
    var searchTextField by remember { mutableStateOf("") }
    var isSearcing by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        viewModel.getNotes()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                if (isSearcing){
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
                        placeholder = { Text(text = "Search notes...") },
                        singleLine = true,
                        value = searchTextField,
                        onValueChange = {
                            searchTextField=it
                            viewModel.searchNotes(searchTextField)
                            // viewModel.getNotes()
                        },
                        label = { Text(text = "Searching")}

                    )
                }
                else{
                    Text(text = "NoteApp")
                }
            },
                actions = {
                    if (isSearcing){
                        IconButton(onClick = {
                            isSearcing=false
                            searchTextField=""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.close), contentDescription = "")
                        }
                    }
                    else{
                        IconButton(onClick = {
                            isSearcing=true
                            searchTextField=""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.search), contentDescription = "")
                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                items(notesList) { note ->
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = note.title, style = MaterialTheme.typography.bodyLarge)
                                Icon(
                                    modifier = Modifier.clickable {
                                        viewModel.deleteNotes(note)
                                        viewModel.getNotes()
                                    },
                                    painter = painterResource(id = R.drawable.delete), contentDescription = ""
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(text = note.content)
                                Icon(
                                    modifier = Modifier.clickable {
                                        noteToEdit.value = note
                                        showDialog.value = true
                                    },
                                    painter = painterResource(id = R.drawable.edit), contentDescription = ""
                                )
                            }
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    noteToEdit.value = null
                    showDialog.value = true
                }
            ) {
                Icon(painter = painterResource(id = R.drawable.add), contentDescription = "Add Note")
            }
        }
    )

    if (showDialog.value) {
        AddNoteDialog(
            note = noteToEdit.value,
            onDismiss = { showDialog.value = false },
            onSave = { title, description ->
                if (noteToEdit.value != null) {
                    val updatedNote = noteToEdit.value!!.copy(title = title, content = description)
                    viewModel.updateNotes(updatedNote)
                } else {
                    val newNote = Notes(title = title, content = description)
                    viewModel.addNote(newNote)
                }
                showDialog.value = false
                viewModel.getNotes()
            }
        )
    }
}

@Composable
fun AddNoteDialog(
    note: Notes?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    val title = remember { mutableStateOf(note?.title ?: "") }
    val description = remember { mutableStateOf(note?.content ?: "") }

    AlertDialog(
        onDismissRequest = {  },
        title = { Text(text = if (note == null) "Add a Note" else "Edit Note") },
        text = {
            Column {
                // Title TextField
                OutlinedTextField(
                    value = title.value,
                    onValueChange = { title.value = it },
                    label = { Text(text = "Title") }
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Description TextField
                OutlinedTextField(
                    value = description.value,
                    onValueChange = { description.value = it },
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(title.value, description.value)
                }
            ) {
                Text(text = "Save")
            }
        },
        dismissButton = {
            Button(onClick = { onDismiss() }) {
                Text(text = "Cancel")
            }
        }
    )
}
