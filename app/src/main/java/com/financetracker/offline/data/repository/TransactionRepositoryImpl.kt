package com.financetracker.offline.data.repository

import com.financetracker.offline.data.local.dao.TransactionDao
import com.financetracker.offline.data.local.entity.TransactionEntity
import com.financetracker.offline.domain.model.Transaction
import com.financetracker.offline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) : TransactionRepository {

    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toEntity())
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        dao.updateTransaction(transaction.toEntity())
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return dao.getAllTransactions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getTransactionById(id: Long): Transaction? {
        return dao.getTransactionById(id)?.toDomain()
    }

    override fun getMonthlyExpense(startDate: Long): Flow<Double> {
        return dao.getMonthlyExpense(startDate).map { it ?: 0.0 }
    }

    override fun getBalance(): Flow<Double> {
        return dao.getBalance().map { it ?: 0.0 }
    }
}

// Mappers
fun TransactionEntity.toDomain() = Transaction(
    id = id,
    amount = amount,
    type = type,
    categoryId = categoryId,
    bankName = bankName,
    merchant = merchant,
    date = date,
    note = note,
    source = source,
    createdAt = createdAt
)

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    amount = amount,
    type = type,
    categoryId = categoryId,
    bankName = bankName,
    merchant = merchant,
    date = date,
    note = note,
    source = source,
    createdAt = createdAt
)
