package com.financetracker.offline.data.repository

import com.financetracker.offline.data.local.dao.SmsLogDao
import com.financetracker.offline.data.local.entity.SmsLogEntity
import com.financetracker.offline.domain.repository.SmsLogRepository
import javax.inject.Inject

class SmsLogRepositoryImpl @Inject constructor(
    private val dao: SmsLogDao
) : SmsLogRepository {

    override suspend fun isSmsProcessed(smsHash: String): Boolean {
        return dao.isSmsProcessed(smsHash)
    }

    override suspend fun markSmsAsProcessed(smsHash: String, sender: String, timestamp: Long) {
        dao.insertSmsLog(
            SmsLogEntity(
                smsHash = smsHash,
                sender = sender,
                timestamp = timestamp
            )
        )
    }
}
