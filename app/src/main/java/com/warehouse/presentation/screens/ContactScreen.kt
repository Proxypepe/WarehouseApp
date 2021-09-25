package com.warehouse.presentation.screens


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.warehouse.domain.ContactViewModel
import com.warehouse.domain.RequestViewModel
import com.warehouse.presentation.activity.MainActivity
import com.warehouse.repository.model.Contact
import com.warehouse.utils.ContactProvider


@Composable
fun ContactScreen(navController: NavController, activity: MainActivity, requestViewModel: RequestViewModel?,
                    contactViewModel: ContactViewModel?) {
    val context = LocalContext.current
    contactViewModel?.clear()
    val cp = contactViewModel?.let { ContactProvider(context, activity, it) }
    cp?.getContactList()
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val allContacts =  contactViewModel?.getContacts() ?: listOf()
    Column {
        SearchView(textState)
        ContactList(textState, navController, allContacts, requestViewModel)
    }
}



@Composable
fun SearchView(state: MutableState<TextFieldValue>) {


}

@Composable
fun ContactList(state: MutableState<TextFieldValue>, navController: NavController, contacts: List<Contact>,
                requestViewModel: RequestViewModel?) {

    LazyColumn {
        items(contacts) { contact ->
            ContactView(contact, navController, requestViewModel)
        }
    }
}

@Composable
fun ContactView(contact: Contact, navController: NavController?, requestViewModel: RequestViewModel?){
    Card( elevation = 8.dp, modifier = Modifier.padding(10.dp)
            .clickable { requestViewModel?.setContact(contact)
                        navController?.navigate("Make request")}) {
        Column( modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(5.dp)){
                Text(text = "Contact name", modifier = Modifier.width(200.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = contact.name)
            }
            Row(modifier = Modifier.padding(5.dp)){
                Text(text = "Arrival date", modifier = Modifier.width(200.dp))
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = contact.phoneNumber)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchViewPreview() {
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    SearchView(textState)
}


@Preview(showBackground = true)
@Composable
fun ContactListPreview() {
    val textState = remember {
        mutableStateOf(TextFieldValue(""))
    }
    val navController = rememberNavController()
    ContactList(textState, navController, listOf(Contact("Sdsds", "21213413"),
        Contact("wqwq", "131312"),Contact("dd", "131")), null)
}

@Preview(showBackground = true)
@Composable
fun ContactViewPreview() {
    ContactView(Contact("Alex", "(121)1213-1311"), null, null)
}