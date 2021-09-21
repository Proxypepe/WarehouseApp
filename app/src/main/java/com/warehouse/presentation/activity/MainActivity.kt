package com.warehouse.presentation.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.warehouse.R
import com.warehouse.domain.RequestViewModel
import com.warehouse.domain.RequestViewModelFactory
import com.warehouse.presentation.adapter.RequestListAdapter
import com.warehouse.repository.RequestsApplication


class MainActivity : AppCompatActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = RequestListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)



        val button: Button = findViewById(R.id.add_req)
        button.setOnClickListener {
            val intent = Intent(this@MainActivity, NewRequestActivity::class.java)
            startActivity(intent)
        }


        requestViewModel.allRequest.observe(this) { requests ->
            requests.let { adapter.submitList(it) }
        }

    }

}