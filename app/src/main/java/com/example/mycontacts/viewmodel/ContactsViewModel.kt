package com.example.mycontacts.viewmodel

import androidx.lifecycle.ViewModel
import com.example.mycontacts.model.Contact
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactsViewModel : ViewModel() {

    private val _contacts = MutableStateFlow(
        listOf(
            Contact(id = 1, name = "Марія Сидоренко", phone = "+380 50 123 45 67", email = "maria@example.com"),
            Contact(id = 2, name = "Іван Петренко", phone = "+380 68 987 65 43", email = "ivan.p@example.com"),
            Contact(id = 3, name = "Олена Коваленко", phone = "+380 95 555 11 22", email = "olena.kov@example.com")
        )
    )
    val contacts: StateFlow<List<Contact>> = _contacts.asStateFlow()

    fun getContactById(id: Int): Contact? {
        return _contacts.value.find { it.id == id }
    }

    fun addContact(name: String, phone: String, email: String) {
        val nextId = (_contacts.value.maxOfOrNull { it.id } ?: 0) + 1
        val newContact = Contact(
            id = nextId,
            name = name.trim(),
            phone = phone.trim(),
            email = email.trim()
        )
        _contacts.update { it + newContact }
    }

    fun updateContact(id: Int, name: String, phone: String, email: String) {
        _contacts.update { list ->
            list.map { contact ->
                if (contact.id == id) {
                    contact.copy(
                        name = name.trim(),
                        phone = phone.trim(),
                        email = email.trim()
                    )
                } else {
                    contact
                }
            }
        }
    }

    fun deleteContact(id: Int) {
        _contacts.update { list ->
            list.filter { it.id != id }
        }
    }
}