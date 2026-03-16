package com.financetracker.offline.domain.model

enum class TransactionType {
    INCOME, EXPENSE
}

enum class TransactionSource {
    SMS, MANUAL
}

data class Transaction(
    val id: Long = 0,
    val amount: Double,
    val type: TransactionType,
    val categoryId: Long?,
    val bankName: String?,
    val merchant: String?,
    val date: Long,
    val note: String?,
    val source: TransactionSource,
    val createdAt: Long = System.currentTimeMillis()
)
