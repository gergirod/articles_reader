package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article

interface GetArticlesUseCase {

    suspend fun getAllArticles(): ResultWrapper<List<Article>>
}