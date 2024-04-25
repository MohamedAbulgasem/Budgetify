package com.budgetify.app

import com.budgetify.app.model.Budget

sealed class MainScreenUiState {
    data object Loading : MainScreenUiState()
    data class Success(val budget: Budget) : MainScreenUiState()
    data class Failure(val throwable: Throwable) : MainScreenUiState()
}
