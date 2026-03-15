package com.financetracker.offline.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.financetracker.offline.data.local.entity.CategoryEntity
import com.financetracker.offline.data.local.entity.CategoryKeywordEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(category: CategoryEntity): Long

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<CategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategoryKeyword(keyword: CategoryKeywordEntity): Long

    @Query("SELECT * FROM category_keywords WHERE keyword = :keyword LIMIT 1")
    suspend fun getCategoryByKeyword(keyword: String): CategoryKeywordEntity?
}
