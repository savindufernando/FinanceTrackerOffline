package com.financetracker.offline.domain.repository

import com.financetracker.offline.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun insertCategory(category: Category): Long
    fun getAllCategories(): Flow<List<Category>>
    suspend fun assignKeywordToCategory(categoryId: Long, keyword: String)
    suspend fun findCategoryIdByKeyword(keyword: String): Long?
}
