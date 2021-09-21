package com.warehouse.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels

import com.warehouse.domain.RequestViewModel
import com.warehouse.domain.RequestViewModelFactory
import com.warehouse.presentation.compose.StartCardViewList
import com.warehouse.repository.RequestsApplication


class MainActivity : ComponentActivity() {
    private val requestViewModel: RequestViewModel by viewModels {
        RequestViewModelFactory((application as RequestsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StartCardViewList(requestViewModel)
        }
    }
}

