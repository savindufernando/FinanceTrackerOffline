package com.financetracker.offline.domain.usecase

import com.financetracker.offline.domain.repository.CategoryRepository
import javax.inject.Inject

class CategorizeTransactionUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(merchant: String): Long? {
        // 1. Try to find an exact or partial match in user-defined keywords
        val keywords = merchant.lowercase().split(Regex("\\s+"))
        
        for (word in keywords) {
            if (word.length < 3) continue // Skip short words like "to", "at"
            val categoryId = categoryRepository.findCategoryIdByKeyword(word)
            if (categoryId != null) {
                return categoryId
            }
        }
        
        // 2. Return null if no category matches, UI will show as "Uncategorized"
        return null
    }
}
