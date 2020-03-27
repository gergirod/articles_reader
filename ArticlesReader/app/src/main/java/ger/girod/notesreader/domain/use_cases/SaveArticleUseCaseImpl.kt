package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class SaveArticleUseCaseImpl(private val appDataBase: AppDataBase) : SaveArticleUseCase {

    override suspend fun saveArticle(article: Article): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            appDataBase.articleDao().saveArticle(article)
        }
    }
}