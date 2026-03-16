package com.financetracker.offline.domain.repository

import com.financetracker.offline.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun updateTransaction(transaction: Transaction)
    fun getAllTransactions(): Flow<List<Transaction>>
    suspend fun getTransactionById(id: Long): Transaction?
    fun getMonthlyExpense(startDate: Long): Flow<Double>
    fun getBalance(): Flow<Double>
}
