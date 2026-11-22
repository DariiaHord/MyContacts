package com.example.mycontacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycontacts.data.local.ContactEntity
import com.example.mycontacts.data.repository.ContactRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ContactsViewModel(
    private val repository: ContactRepository
) : ViewModel() {

    val contacts: StateFlow<List<ContactEntity>> = repository.contacts.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun getContactById(id: Int): Flow<ContactEntity?> {
        return repository.getContactById(id)
    }

    fun addContact(name: String, phone: String, email: String) {
        viewModelScope.launch {
            repository.addContact(
                ContactEntity(
                    name = name.trim(),
                    phone = phone.trim(),
                    email = email.trim()
                )
            )
        }
    }

    fun updateContact(id: Int, name: String, phone: String, email: String) {
        viewModelScope.launch {
            repository.updateContact(
                ContactEntity(
                    id = id,
                    name = name.trim(),
                    phone = phone.trim(),
                    email = email.trim()
                )
            )
        }
    }

    fun deleteContact(contact: ContactEntity) {
        viewModelScope.launch {
            repository.deleteContact(contact)
        }
    }
}