package com.example.mycontacts

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mycontacts.data.local.DatabaseProvider
import com.example.mycontacts.data.repository.ContactRepository
import com.example.mycontacts.ui.AppNavigation
import com.example.mycontacts.viewmodel.ContactsViewModel
import com.example.mycontacts.viewmodel.ContactsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val context = LocalContext.current
                    val database = remember { DatabaseProvider.getDatabase(context) }
                    val repository = remember { ContactRepository(database.contactDao()) }
                    val viewModel: ContactsViewModel = viewModel(
                        factory = ContactsViewModelFactory(repository)
                    )

                    AppNavigation(viewModel = viewModel)
                }
            }
        }
    }
}