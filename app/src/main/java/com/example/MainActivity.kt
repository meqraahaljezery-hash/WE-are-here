package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.ui.MaqraahApp
import com.example.ui.MaqraahViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: MaqraahViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaqraahApp(viewModel = viewModel)
        }
    }
}
