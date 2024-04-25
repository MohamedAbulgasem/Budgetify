package com.budgetify.app.model

data class Budget(
    val totalAmount: Double,
    val currency: Currency,
    val categories: List<Category>,
)

data class Category(
    val title: String,
    val items: List<BudgetItem>
)

data class BudgetItem(
    val title: String,
    val amount: Double
)

enum class Currency(val symbol: String) {
    ZAR("R"),
    USD("$"),
}
