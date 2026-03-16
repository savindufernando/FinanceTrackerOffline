package com.financetracker.offline.domain.repository

interface SmsLogRepository {
    suspend fun isSmsProcessed(smsHash: String): Boolean
    suspend fun markSmsAsProcessed(smsHash: String, sender: String, timestamp: Long)
}
