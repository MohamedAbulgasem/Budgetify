package com.budgetify.app.data.model

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

data class Budget(
    val totalAmount: Double,
    val currency: Currency,
    val categories: List<Category>,
)

sealed class Category {

    abstract val items: List<BudgetItem>

    val name: String by lazy { javaClass.simpleName.toString() }

    val totalAmount: Double
        get() = items.sumOf { it.amount }

    val color: Color
        @Composable get() = when (this) {
            is Investments -> MaterialTheme.colorScheme.primary
            is Savings -> Color(0xFF54A276)
            is Expenses -> MaterialTheme.colorScheme.tertiary
        }

    data class Investments(override val items: List<BudgetItem>) : Category()

    data class Savings(override val items: List<BudgetItem>) : Category()

    data class Expenses(override val items: List<BudgetItem>) : Category()
}

data class BudgetItem(
    val title: String,
    val amount: Double
)

enum class Currency(val symbol: String) {
    ZAR("R"),
    USD("$"),
}
