package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle

class UpdateCategoryUseCaseImpl(val articleDao: ArticleDao) : UpdateCategoryUseCase {

    override suspend fun updateCategory(category: Category): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.updateCategory(category)
        }
    }
}