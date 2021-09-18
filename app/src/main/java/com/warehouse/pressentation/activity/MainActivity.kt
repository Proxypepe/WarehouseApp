package com.warehouse.pressentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

import com.warehouse.R


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button: Button = findViewById(R.id.add_req)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, NewRequestActivity::class.java)
            startActivity(intent)
        }
    }

}