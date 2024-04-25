package com.budgetify.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.budgetify.app.model.Budget
import com.budgetify.app.model.Category
import com.budgetify.app.ui.theme.BudgetifyTheme
import kotlin.math.roundToInt

@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        when (uiState) {
            is MainScreenUiState.Loading -> {
                Text("Loading...")
            }

            is MainScreenUiState.Success -> {
                BudgetScreen(uiState.budget)
            }

            is MainScreenUiState.Failure -> {
                Text("Error: ${uiState.throwable.message}")
            }
        }
    }
}

@Composable
private fun BudgetScreen(
    budget: Budget,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column {
            val moneyIn = budget.currency.symbol + " " + budget.totalAmount.roundToInt()
            Text(
                text = "Total money in $moneyIn",
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(40.dp)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.height(20.dp))

            budget.categories.forEach { category ->
                CategoryItem(category)
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: Category,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = category.title,
            color = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(40.dp)
        )

        category.items.forEach { budgetItem ->
            Text(
                text = budgetItem.title + " : " + budgetItem.amount.roundToInt(),
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(40.dp)
                    .background(Color.Gray)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainScreenPreview() {
    BudgetifyTheme {
        MainScreen(uiState = MainScreenUiState.Loading)
    }
}
