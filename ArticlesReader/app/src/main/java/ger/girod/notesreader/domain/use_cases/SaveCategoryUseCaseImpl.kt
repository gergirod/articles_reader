package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class SaveCategoryUseCaseImpl(val appDataBase: AppDataBase) : SaveCategoryUseCase {
    override suspend fun saveCategory(category: Category): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            appDataBase.articleDao().saveCategory(category)
        }
    }
}