package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class MarkArticleAsReadUseCaseImpl(private val articleDao: ArticleDao) : MarkArticleAsReadUseCase {

    override suspend fun markArticleAsRead(article: Article): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.updateArticle(article)
        }
    }
}