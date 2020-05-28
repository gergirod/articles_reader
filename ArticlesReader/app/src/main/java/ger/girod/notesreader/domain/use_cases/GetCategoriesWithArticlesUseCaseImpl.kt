package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.daos.CategoryWithArticles
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class GetCategoriesWithArticlesUseCaseImpl(val articleDao: ArticleDao) : GetCategoriesWithArticlesUseCase {
    override suspend fun getCategoriesWithArtiles(): ResultWrapper<List<CategoryWithArticles>> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.getAllCategoriesWithArticles()
        }
    }
}