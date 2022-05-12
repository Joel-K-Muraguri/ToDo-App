package com.joel.todo_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.joel.todo_app.add_edit_todo.AddEditScreen
import com.joel.todo_app.listscreens.TodoListScreen
import com.joel.todo_app.ui.TodoViewModel
import com.joel.todo_app.util.Routes

@Composable
fun NavigationFile(
    navController: NavHostController
){
    NavHost(navController = navController, startDestination= Routes.TODO_LIST ){
        composable(Routes.TODO_LIST){
            TodoListScreen(onNavigate = {
                navController.navigate(it.route)
            },
                mViewModel = mViewModel
            )
        }
        composable(
            route = Routes.TODO_EDIT_ADD + "?todId={todoId}",
            arguments = listOf(
                navArgument(name = "todoId"){
                    type = NavType.IntType
                    defaultValue =-1
                }
            )
        ){
            AddEditScreen(onPopBackStack = {
                navController.popBackStack()
            },

            )


        }
    }

}