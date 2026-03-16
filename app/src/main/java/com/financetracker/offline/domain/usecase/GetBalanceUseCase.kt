package com.financetracker.offline.domain.usecase

import com.financetracker.offline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {
    operator fun invoke(): Flow<Double> {
        return transactionRepository.getBalance()
    }
}
