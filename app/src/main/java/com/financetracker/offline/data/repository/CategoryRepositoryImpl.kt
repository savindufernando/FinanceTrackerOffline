package com.financetracker.offline.data.repository

import com.financetracker.offline.data.local.dao.CategoryDao
import com.financetracker.offline.data.local.entity.CategoryEntity
import com.financetracker.offline.data.local.entity.CategoryKeywordEntity
import com.financetracker.offline.domain.model.Category
import com.financetracker.offline.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : CategoryRepository {

    override suspend fun insertCategory(category: Category): Long {
        return dao.insertCategory(category.toEntity())
    }

    override fun getAllCategories(): Flow<List<Category>> {
        return dao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun assignKeywordToCategory(categoryId: Long, keyword: String) {
        dao.insertCategoryKeyword(CategoryKeywordEntity(categoryId = categoryId, keyword = keyword))
    }

    override suspend fun findCategoryIdByKeyword(keyword: String): Long? {
        return dao.getCategoryByKeyword(keyword)?.categoryId
    }
}

// Mappers
fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name,
    type = type,
    isDefault = isDefault
)

fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    type = type,
    isDefault = isDefault
)
