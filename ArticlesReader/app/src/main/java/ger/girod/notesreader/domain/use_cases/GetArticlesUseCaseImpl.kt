package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class GetArticlesUseCaseImpl(private val articleDao: ArticleDao) : GetArticlesUseCase {

    override suspend fun getAllArticles(): ResultWrapper<List<Article>> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.getArticles()
        }
    }
}