package com.warehouse.domain

import androidx.lifecycle.ViewModel
import com.warehouse.repository.model.Contact


class ContactViewModel: ViewModel() {
    private var contacts = mutableListOf<Contact>()

    fun addContact(name: String, phoneNumber: String){
        val contact = Contact(name, phoneNumber)
        contacts.add(contact)
    }

    fun getContacts(): List<Contact>{
        contacts.removeIf { it.name == "" && it.phoneNumber == "" }
        return contacts
    }

    fun clear(){
        contacts.clear()
    }
}