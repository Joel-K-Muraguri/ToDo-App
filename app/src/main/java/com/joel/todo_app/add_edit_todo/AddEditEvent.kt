package com.joel.todo_app.add_edit_todo

import androidx.compose.foundation.layout.PaddingValues

sealed class AddEditEvent(){
    data class OnTitleChange(val title: String): AddEditEvent()
    data class OnDescriptionChange(val description : String): AddEditEvent()
    object OnAddTodoClick : AddEditEvent()

}
