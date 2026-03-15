package com.financetracker.offline.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.financetracker.offline.domain.model.TransactionSource
import com.financetracker.offline.domain.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
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
