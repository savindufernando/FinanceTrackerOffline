package com.financetracker.offline.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.financetracker.offline.domain.model.TransactionType

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val type: TransactionType,
    val isDefault: Boolean = false
)
