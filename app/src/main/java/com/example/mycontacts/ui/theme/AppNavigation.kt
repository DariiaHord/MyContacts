package com.example.mycontacts.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycontacts.viewmodel.ContactsViewModel

@Composable
fun AppNavigation(viewModel: ContactsViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "list"
    ) {
        composable("list") {
            ContactsListScreen(
                viewModel = viewModel,
                onContactClick = { contactId ->
                    navController.navigate("details/$contactId")
                },
                onAddClick = {
                    navController.navigate("add")
                }
            )
        }

        composable("add") {
            AddContactScreen(
                viewModel = viewModel,
                contactId = null,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "edit/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId")
            AddContactScreen(
                viewModel = viewModel,
                contactId = contactId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = "details/{contactId}",
            arguments = listOf(navArgument("contactId") { type = NavType.IntType })
        ) { backStackEntry ->
            val contactId = backStackEntry.arguments?.getInt("contactId") ?: 0
            DetailsContactScreen(
                contactId = contactId,
                viewModel = viewModel,
                onEditClick = { id ->
                    navController.navigate("edit/$id")
                },
                onDeleteSuccess = {
                    navController.popBackStack(route = "list", inclusive = false)
                },
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}