package com.budgetify.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.budgetify.app.MainScreenUiState.Loading
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

class MainViewModel(
    private val budgetRepository: BudgetRepository = BudgetRepositoryImpl
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainScreenUiState>(Loading)
    val uiState: StateFlow<MainScreenUiState> = _uiState

    init {
        loadBudget()
    }

    private fun loadBudget() = runCoroutine {
        budgetRepository.fetchBudget()
            .onFailure { error ->
                _uiState.update { MainScreenUiState.Failure(error) }
            }
            .onSuccess { budget ->
                _uiState.update { MainScreenUiState.Success(budget) }
            }
    }

    private fun runCoroutine(
        context: CoroutineContext = EmptyCoroutineContext,
        start: CoroutineStart = CoroutineStart.DEFAULT,
        block: suspend CoroutineScope.() -> Unit
    ) = viewModelScope.launch(context, start, block)
}
