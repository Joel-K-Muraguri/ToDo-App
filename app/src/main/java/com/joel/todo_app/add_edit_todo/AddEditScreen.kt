package com.joel.todo_app.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.todo_app.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditScreen(
    onPopBackStack : () -> Unit,
    mViewModel: AddEditTodoViewModel
){
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        mViewModel.uiEvent.collect { event ->
            when(event){
                is UiEvent.PopBackStack -> onPopBackStack
                is UiEvent.SnackBar ->
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = { mViewModel.onEvent(AddEditEvent.OnAddTodoClick) }) {
                Icon(imageVector = Icons.Filled.Check, contentDescription = "Add")
            }
        }
    ) {
        Column() {
            TextField(
                value = mViewModel.title,
                onValueChange = {mViewModel.onEvent(AddEditEvent.OnTitleChange(it))},
                label = { Text(text = "Title")}
            )
            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                value = mViewModel.description,
                onValueChange = {mViewModel.onEvent(AddEditEvent.OnDescriptionChange(it))},
                label = { Text(text = "Description")},
                maxLines = 20,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

