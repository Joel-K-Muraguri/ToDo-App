package com.joel.todo_app.listscreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joel.todo_app.ui.ToDoAppEvent
import com.joel.todo_app.ui.TodoViewModel
import com.joel.todo_app.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    mViewModel : TodoViewModel
){
    val todos = mViewModel.todo.collectAsState(initial = emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1 = true){
        mViewModel.uiEvent.collect { event ->
            when (event){
                is UiEvent.SnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action
                    )
                    if (result == SnackbarResult.ActionPerformed){
                        mViewModel.onEvents(ToDoAppEvent.UndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mViewModel.onEvents(ToDoAppEvent.OnAddTodoClick)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "ADD")
            }
        }

    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()){
            items(todos.value){ todo ->
                TodoItem(
                    todo = todo,
                    onEvents = mViewModel::onEvents,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            mViewModel.onEvents(ToDoAppEvent.OnTodoClick(todo))
                        }
                        .padding(20.dp)
                
                )
            }
            }
        }
    }
