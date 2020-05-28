package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class GetCategoriesUseCaseImpl(val articleDao: ArticleDao) : GetCategoriesUseCase {

    override suspend fun getCategories(): ResultWrapper<List<Category>> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.getCategories()
        }
    }
}