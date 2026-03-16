package com.financetracker.offline.di

import android.app.Application
import androidx.room.Room
import com.financetracker.offline.data.local.FinanceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideFinanceDatabase(app: Application): FinanceDatabase {
        return Room.databaseBuilder(
            app,
            FinanceDatabase::class.java,
            FinanceDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: FinanceDatabase) = db.transactionDao

    @Provides
    @Singleton
    fun provideCategoryDao(db: FinanceDatabase) = db.categoryDao

    @Provides
    @Singleton
    fun provideSmsLogDao(db: FinanceDatabase) = db.smsLogDao
}
