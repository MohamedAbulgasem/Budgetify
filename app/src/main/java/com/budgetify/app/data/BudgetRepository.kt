package com.budgetify.app.data

import com.budgetify.app.data.model.Budget
import com.budgetify.app.data.model.BudgetItem
import com.budgetify.app.data.model.Category
import com.budgetify.app.data.model.Currency

interface BudgetRepository {
    suspend fun fetchBudget(): Result<Budget>
    suspend fun updateBudget(budget: Budget)
}

object BudgetRepositoryImpl : BudgetRepository {

    private var budget: Budget? = mockBudget

    override suspend fun fetchBudget(): Result<Budget> = runCatching { checkNotNull(budget) }

    override suspend fun updateBudget(budget: Budget) {
        BudgetRepositoryImpl.budget = budget
    }
}

val mockBudget = Budget(
    totalAmount = 30000.0,
    currency = Currency.ZAR,
    categories = listOf(
        Category(
            name = "Investments",
            items = listOf(
                BudgetItem(
                    title = "Tax-free acc",
                    amount = 3000.0
                ),
                BudgetItem(
                    title = "S&P 500",
                    amount = 1000.0
                ),
                BudgetItem(
                    title = "Local stocks",
                    amount = 1000.0
                ),
                BudgetItem(
                    title = "Crypto",
                    amount = 1000.0
                ),
            )
        ),
        Category(
            name = "Savings",
            items = listOf(
                BudgetItem(
                    title = "Holiday",
                    amount = 2000.0
                ),
                BudgetItem(
                    title = "Emergency Fund",
                    amount = 1000.0
                ),
            )
        ),
        Category(
            name = "Expenses",
            items = listOf(
                BudgetItem(
                    title = "Debit Orders",
                    amount = 13000.0
                ),
                BudgetItem(
                    title = "Food",
                    amount = 5000.0
                ),
                BudgetItem(
                    title = "Other spending",
                    amount = 3000.0
                ),
            )
        ),
    )
)
