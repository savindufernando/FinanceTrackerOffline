package com.financetracker.offline.domain.usecase

import com.financetracker.offline.domain.model.TransactionType
import com.financetracker.offline.domain.repository.CategoryRepository
import com.financetracker.offline.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

data class CategoryExpense(
    val categoryName: String,
    val amount: Double,
    val percentage: Float = 0f
)

class GetCategoryDistributionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) {
    operator fun invoke(): Flow<List<CategoryExpense>> {
        return combine(
            transactionRepository.getAllTransactions(),
            categoryRepository.getAllCategories()
        ) { transactions, categories ->
            val expenses = transactions.filter { it.type == TransactionType.EXPENSE }
            val totalExpense = expenses.sumOf { it.amount }
            
            if (totalExpense == 0.0) return@combine emptyList()

            val categoryMap = categories.associateBy { it.id }

            expenses.groupBy { it.categoryId }
                .map { (categoryId, txList) ->
                    val categoryName = categoryMap[categoryId]?.name ?: "Uncategorized"
                    val amount = txList.sumOf { it.amount }
                    CategoryExpense(
                        categoryName = categoryName,
                        amount = amount,
                        percentage = (amount / totalExpense).toFloat() * 100f
                    )
                }
                .sortedByDescending { it.amount }
        }
    }
}
