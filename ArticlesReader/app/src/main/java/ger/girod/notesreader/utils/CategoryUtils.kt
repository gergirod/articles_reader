package ger.girod.notesreader.utils

import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category

object CategoryUtils {

    fun getCategoryPositionInList(article: Article, list: List<Category>) : Int {

        for(category in list.withIndex()) {

            if(category.value.id.equals(article.categoryId)) {
                return category.index
            }

        }

        return 0

    }
}