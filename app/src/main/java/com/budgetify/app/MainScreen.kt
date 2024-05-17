package com.budgetify.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.budgetify.app.data.mockBudget
import com.budgetify.app.ui.screen.display.DisplayScreen
import com.budgetify.app.ui.screen.input.InputScreen
import com.budgetify.app.ui.theme.BudgetifyTheme

@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    modifier: Modifier = Modifier,
) {
    Surface(modifier = modifier.fillMaxSize()) {
        uiState.budget?.let { budget ->
            DisplayScreen(budget)
        } ?: run {
            InputScreen()
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    BudgetifyTheme {
        MainScreen(uiState = MainScreenUiState(mockBudget))
    }
}
