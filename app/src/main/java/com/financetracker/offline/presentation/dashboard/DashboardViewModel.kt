package com.financetracker.offline.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financetracker.offline.domain.usecase.GetBalanceUseCase
import com.financetracker.offline.domain.usecase.GetCategoryDistributionUseCase
import com.financetracker.offline.domain.usecase.GetMonthlyExpenseUseCase
import com.financetracker.offline.domain.usecase.InitializeCategoriesUseCase
import com.financetracker.offline.domain.usecase.CategoryExpense
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getMonthlyExpenseUseCase: GetMonthlyExpenseUseCase,
    private val getCategoryDistributionUseCase: GetCategoryDistributionUseCase,
    private val initializeCategoriesUseCase: InitializeCategoriesUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            initializeCategoriesUseCase()
        }
    }

    val balance: StateFlow<Double> = getBalanceUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val monthlyExpense: StateFlow<Double> = getMonthlyExpenseUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val categoryDistribution: StateFlow<List<CategoryExpense>> = getCategoryDistributionUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}
