package com.joel.todo_app.util

// Holds events to be used in the app
sealed class UiEvent(){
    object PopBackStack : UiEvent()
    data class Navigate (val route : String) : UiEvent()
    data class SnackBar (val message : String, val action : String? = null ) :UiEvent()
}
