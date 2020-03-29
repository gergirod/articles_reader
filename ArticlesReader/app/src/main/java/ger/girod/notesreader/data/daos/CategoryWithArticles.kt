package ger.girod.notesreader.data.daos

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category

data class CategoryWithArticles (

    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "category_id")
    val articles : List<Article>
)