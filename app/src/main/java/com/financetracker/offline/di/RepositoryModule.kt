package com.financetracker.offline.di

import com.financetracker.offline.data.repository.CategoryRepositoryImpl
import com.financetracker.offline.data.repository.SmsLogRepositoryImpl
import com.financetracker.offline.data.repository.TransactionRepositoryImpl
import com.financetracker.offline.domain.repository.CategoryRepository
import com.financetracker.offline.domain.repository.SmsLogRepository
import com.financetracker.offline.domain.repository.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository

    @Binds
    @Singleton
    abstract fun bindSmsLogRepository(
        smsLogRepositoryImpl: SmsLogRepositoryImpl
    ): SmsLogRepository
}
