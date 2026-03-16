package com.financetracker.offline.domain.usecase

import com.financetracker.offline.domain.model.Category
import com.financetracker.offline.domain.model.TransactionType
import com.financetracker.offline.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class InitializeCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke() {
        val existing = categoryRepository.getAllCategories().first()
        if (existing.isNotEmpty()) return

        val defaultExpenses = listOf("Food", "Fuel", "Transport", "Shopping", "Bills", "Entertainment", "Other Expense")
        val defaultIncomes = listOf("Salary", "Business", "Transfer", "Other Income")

        defaultExpenses.forEach { name ->
            val id = categoryRepository.insertCategory(
                Category(name = name, type = TransactionType.EXPENSE, isDefault = true)
            )
            // Assign some basic keywords
            categoryRepository.assignKeywordToCategory(id, name.lowercase())
            if (name == "Food") {
                categoryRepository.assignKeywordToCategory(id, "zomato")
                categoryRepository.assignKeywordToCategory(id, "swiggy")
                categoryRepository.assignKeywordToCategory(id, "restaurant")
            }
        }

        defaultIncomes.forEach { name ->
            val id = categoryRepository.insertCategory(
                Category(name = name, type = TransactionType.INCOME, isDefault = true)
            )
            categoryRepository.assignKeywordToCategory(id, name.lowercase())
            if (name == "Salary") {
                categoryRepository.assignKeywordToCategory(id, "payroll")
            }
        }
    }
}
