package com.warehouse.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import androidx.core.app.ActivityCompat
import com.warehouse.domain.ContactViewModel


class ContactProvider(private val context: Context, private val activity: Activity,private var contactViewModel: ContactViewModel) {

    private var readed = false

    private fun checkPermission(): Boolean {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                Array(1) { Manifest.permission.READ_CONTACTS },
                111
            )
            return true
        }
        return true
    }

    private fun readContacts() {
        val cr: ContentResolver = context.contentResolver
        val cur: Cursor? = cr.query(
            ContactsContract.Contacts.CONTENT_URI,
            null, null, null, null
        )
        if ((cur?.getCount() ?: 0) > 0) {
            while (cur != null && cur.moveToNext()) {
                val id: String = cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts._ID)
                )
                val name: String = cur.getString(
                    cur.getColumnIndex(
                        ContactsContract.Contacts.DISPLAY_NAME
                    )
                )
                if (cur.getInt(
                        cur.getColumnIndex(
                            ContactsContract.Contacts.HAS_PHONE_NUMBER
                        )
                    ) > 0
                ) {
                    val pCur: Cursor? = cr.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", arrayOf(id), null
                    )
                    if (pCur != null) {
                        while (pCur.moveToNext()) {
                            val phoneNo: String = pCur.getString(
                                pCur.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER
                                )
                            )
                            contactViewModel.addContact(name, phoneNo)
                            Log.i("getName", "Name: $name")
                            Log.i("getNumber", "Phone Number: $phoneNo")
                        }
                    }
                    pCur?.close()
                }
            }
        }
        cur?.close()
    }

    @SuppressLint("Range")
    fun getContactList() {
        val hasPermission = checkPermission()
        if (hasPermission && !readed){
            readContacts()
            readed = true
        }
    }
}
