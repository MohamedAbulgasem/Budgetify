package com.budgetify.app

import com.budgetify.app.data.BudgetRepository
import com.budgetify.app.data.BudgetRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl
) : BaseViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState())
    val uiState: StateFlow<MainScreenUiState> = _uiState

    init {
        loadBudget()
    }

    private fun loadBudget() = runCoroutine {
        budgetRepository.fetchBudget()
            .onFailure { error ->
                //_uiState.update { MainScreenUiState.Failure(error) }
            }
            .onSuccess { budget ->
                _uiState.update { MainScreenUiState(budget) }
            }
    }
}
