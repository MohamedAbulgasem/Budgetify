package com.budgetify.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.budgetify.app.ui.theme.BudgetifyTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetifyTheme {
                val uiState by viewModel.uiState.collectAsState()

                MainScreen(
                    uiState = uiState
                )
            }
        }
    }
}
