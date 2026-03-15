package com.financetracker.offline.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sms_logs")
data class SmsLogEntity(
    @PrimaryKey
    val smsHash: String,
    val sender: String,
    val timestamp: Long,
    val processedAt: Long = System.currentTimeMillis()
)
