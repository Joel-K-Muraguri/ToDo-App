package com.joel.todo_app.listscreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joel.todo_app.data.Todo
import com.joel.todo_app.ui.ToDoAppEvent



@Composable
fun TodoItem(
    todo: Todo,
    onEvents:(ToDoAppEvent) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column() {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Input title and delete icon
                Text(
                    text = todo.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                IconButton(onClick = {
                    onEvents(ToDoAppEvent.OnDeleteTodoClick(todo = todo))
                }) {
                    Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete Icon")
                }
            }
            //Input Description
            todo.description?.let {
                Spacer(modifier = Modifier.height(20.dp))
                Text(text = it)
            }
        }
        //Input checkbox
        todo.isDone?.let {
            Checkbox(checked = it,
                onCheckedChange = { isChecked ->
                    onEvents(ToDoAppEvent.OnTodoChange(todo, isChecked))
                })
        }
    }
}

