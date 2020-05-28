package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class SaveArticleUseCaseImpl(private val articleDao: ArticleDao) : SaveArticleUseCase {

    override suspend fun saveArticle(article: Article): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.saveArticle(article)
        }
    }
}