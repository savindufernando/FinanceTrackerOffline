package com.financetracker.offline.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.financetracker.offline.data.local.entity.SmsLogEntity

@Dao
interface SmsLogDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSmsLog(smsLog: SmsLogEntity): Long

    @Query("SELECT EXISTS(SELECT 1 FROM sms_logs WHERE smsHash = :smsHash LIMIT 1)")
    suspend fun isSmsProcessed(smsHash: String): Boolean
}
