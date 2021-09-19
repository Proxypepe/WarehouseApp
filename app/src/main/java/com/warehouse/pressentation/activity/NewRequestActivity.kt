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
        var date: Date? = null
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        ){  _ , year, month, day ->
            date = Date(year, month, day)
        }

        val productName: EditText = findViewById(R.id.productNamePT)
        val amount: EditText = findViewById(R.id.amountPT)
        val warehousePlace: EditText = findViewById(R.id.warehousePlacePT)
        val status: EditText = findViewById(R.id.statusPT)

        val button: Button = findViewById(R.id.add_button)
        button.setOnClickListener {
            if (productName.text.isNotEmpty() && amount.text.isNotEmpty() &&
                warehousePlace.text.isNotEmpty() && status.text.isNotEmpty())
            {
                requestViewModel.setRequest(
                    productName=productName.text.toString(), amount=Integer.parseInt(amount.text.toString()),
                    warehousePlace=Integer.parseInt(warehousePlace.text.toString()), status=status.text.toString(),
                    arrivalDate=date)

                requestViewModel.writeRequest()
                finish()
            } else  {
                Toast.makeText(this@NewRequestActivity,
                          "Fill in all the fields",  Toast.LENGTH_LONG).show()
            }
        }
    }
}