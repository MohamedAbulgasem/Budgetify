package com.budgetify.app.data.model

data class Budget(
    val totalAmount: Double,
    val currency: Currency,
    val categories: List<Category>,
)

data class Category(
    val name: String,
    val items: List<BudgetItem>
) {
    val totalAmount: Double = items.sumOf { it.amount }
}

data class BudgetItem(
    val title: String,
    val amount: Double
)

enum class Currency(val symbol: String) {
    ZAR("R"),
    USD("$"),
}
