package com.financetracker.offline.presentation.add_transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.financetracker.offline.domain.model.Category
import com.financetracker.offline.domain.model.Transaction
import com.financetracker.offline.domain.model.TransactionSource
import com.financetracker.offline.domain.model.TransactionType
import com.financetracker.offline.domain.repository.CategoryRepository
import com.financetracker.offline.domain.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    val categories: StateFlow<List<Category>> = categoryRepository.getAllCategories()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _saveStatus = MutableStateFlow<Boolean?>(null)
    val saveStatus: StateFlow<Boolean?> = _saveStatus

    fun saveTransaction(
        amountStr: String,
        type: TransactionType,
        categoryId: Long?,
        note: String
    ) {
        val amount = amountStr.toDoubleOrNull()
        if (amount == null || amount <= 0) {
            _saveStatus.value = false
            return
        }

        viewModelScope.launch {
            try {
                val transaction = Transaction(
                    amount = amount,
                    type = type,
                    categoryId = categoryId,
                    date = System.currentTimeMillis(),
                    note = note.ifBlank { "Manual Entry" },
                    merchant = "Manual",
                    bankName = "Cash/Manual",
                    source = TransactionSource.MANUAL
                )
                transactionRepository.insertTransaction(transaction)
                _saveStatus.value = true
            } catch (e: Exception) {
                e.printStackTrace()
                _saveStatus.value = false
            }
        }
    }

    fun resetStatus() {
        _saveStatus.value = null
    }
}
