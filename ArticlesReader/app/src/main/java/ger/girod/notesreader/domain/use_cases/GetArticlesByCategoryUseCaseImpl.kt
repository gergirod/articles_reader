package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class GetArticlesByCategoryUseCaseImpl(val articleDao: ArticleDao) : GetArticlesByCategoryUseCase {
    override suspend fun getArticlesByCategory(categoryId: Long) : ResultWrapper<List<Article>>{
        return exequteRequest(Dispatchers.IO){
            articleDao.getArticlesByCategoryId(categoryId)
        }

    }
}