package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article

interface SaveArticleUseCase {

    suspend fun saveArticle(article: Article): ResultWrapper<Unit>
}