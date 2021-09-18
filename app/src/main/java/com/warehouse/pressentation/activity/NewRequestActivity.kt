package com.warehouse.pressentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import com.warehouse.R
import com.warehouse.domain.RequestViewModel
import com.warehouse.domain.RequestViewModelFactory
import com.warehouse.repository.RequestsApplication
import androidx.activity.viewModels
import com.warehouse.repository.database.entity.Request


import java.util.*

class NewRequestActivity : AppCompatActivity() {

    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_request)

        val datePicker = findViewById<DatePicker>(R.id.date_Picker)
        val today = Calendar.getInstance()

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ){  view, year, month, day ->
            val month = month + 1
            val msg = "You Selected: $day/$month/$year"
            Toast.makeText(this@NewRequestActivity, msg, Toast.LENGTH_SHORT).show()
        }



        val productName: EditText? = findViewById(R.id.productNamePT)
        val amount: EditText? = findViewById(R.id.amountPT)
        val warehousePlace: EditText? = findViewById(R.id.warehousePlacePT)
        val status: EditText? = findViewById(R.id.statusPT)

        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener {
            val req = Request(
                productName=productName?.text.toString(), amount=amount?.text.toString().toInt(),
                warehousePlace=warehousePlace?.text.toString().toInt(), status=status?.text.toString(),
                arrivalDate=null)
            val msg = productName?.text.toString() + amount?.text.toString() + warehousePlace?.text.toString() + status?.text.toString()
            requestViewModel.insert(req)

            Toast.makeText(this@NewRequestActivity, msg, Toast.LENGTH_SHORT).show()

        }

    }
}