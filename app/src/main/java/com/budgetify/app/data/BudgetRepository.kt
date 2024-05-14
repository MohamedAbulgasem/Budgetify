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
    totalAmount = 59000.0,
    currency = Currency.ZAR,
    categories = listOf(
        Category(
            name = "Investments",
            items = listOf(
                BudgetItem(
                    title = "Crypto",
                    amount = 5000.0
                ),
                BudgetItem(
                    title = "Tax-free",
                    amount = 3000.0
                ),
                BudgetItem(
                    title = "Crypto",
                    amount = 5000.0
                ),
                BudgetItem(
                    title = "Tax-free",
                    amount = 3000.0
                )
            )
        ),
        Category(
            name = "Savings",
            items = listOf(
                BudgetItem(
                    title = "Tyme Emergency Fund",
                    amount = 25000.0
                )
            )
        ),
        Category(
            name = "Expenses",
            items = listOf(
                BudgetItem(
                    title = "Debit Orders",
                    amount = 9000.0
                ),
                BudgetItem(
                    title = "Day-to-day spending",
                    amount = 17000.0
                ),
                BudgetItem(
                    title = "Debit Orders",
                    amount = 9000.0
                ),
                BudgetItem(
                    title = "Day-to-day spending",
                    amount = 17000.0
                )
            )
        ),
    )
)
