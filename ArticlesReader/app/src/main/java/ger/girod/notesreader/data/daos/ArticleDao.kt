package ger.girod.notesreader.data.daos

import androidx.room.*
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category

@Dao
interface ArticleDao {

    @Insert
    suspend fun saveArticle(article: Article): Unit

    @Insert
    suspend fun saveCategory(category: Category) : Unit

    @Delete
    suspend fun deleteArticle(article: Article): Unit

    @Query("SELECT * FROM article_table ORDER BY date_saved DESC")
    suspend fun getArticles(): List<Article>

    @Update
    suspend fun updateArticle(article: Article): Unit

    @Transaction
    @Query("SELECT * FROM category_table")
    suspend fun getAllCategoriesWithArticles() : List<Category>

}