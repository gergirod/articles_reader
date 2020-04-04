package ger.girod.notesreader.data.daos

import androidx.room.*
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category

@Dao
interface ArticleDao {

    @Insert
    suspend fun saveArticle(article: Article): Unit

    @Insert
    suspend fun saveCategory(category: Category) : Long

    @Delete
    suspend fun deleteArticle(article: Article): Unit

    @Delete
    suspend fun deleteCategory(category: Category) : Unit

    @Update
    suspend fun updateCategory(category: Category) : Unit

    @Update
    suspend fun updateArticle(article: Article): Unit

    @Query("SELECT * FROM article_table ORDER BY date_saved DESC")
    suspend fun getArticles(): List<Article>

    @Query("SELECT * FROM article_table WHERE category_id = :categoryId ORDER BY date_saved DESC")
    suspend fun getArticlesByCategoryId(categoryId : Long): List<Article>

    @Query("SELECT * FROM category_table")
    suspend fun getCategories(): List<Category>

    @Transaction
    @Query("SELECT * FROM category_table")
    suspend fun getAllCategoriesWithArticles() : List<CategoryWithArticles>

    @Query("SELECT COUNT(*) FROM category_table")
    suspend fun getCategoriesCount() : Int

    @Query("SELECT * FROM category_table WHERE id = :categoryId")
    suspend fun getCategory(categoryId: Long) : Category

    @Query("SELECT * FROM category_table Limit 1")
    suspend fun getRandomCategoryFromList() : Category

}