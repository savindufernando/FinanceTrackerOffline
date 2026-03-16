package com.financetracker.offline.domain.model

data class Category(
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val isDefault: Boolean = false
)
