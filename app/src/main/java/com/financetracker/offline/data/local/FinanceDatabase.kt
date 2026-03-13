package com.financetracker.offline.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.financetracker.offline.data.local.dao.CategoryDao
import com.financetracker.offline.data.local.dao.SmsLogDao
import com.financetracker.offline.data.local.dao.TransactionDao
import com.financetracker.offline.data.local.entity.CategoryEntity
import com.financetracker.offline.data.local.entity.CategoryKeywordEntity
import com.financetracker.offline.data.local.entity.SmsLogEntity
import com.financetracker.offline.data.local.entity.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        CategoryKeywordEntity::class,
        SmsLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class FinanceDatabase : RoomDatabase() {
    abstract val transactionDao: TransactionDao
    abstract val categoryDao: CategoryDao
    abstract val smsLogDao: SmsLogDao

    companion object {
        const val DATABASE_NAME = "finance_tracker_db"
    }
}
