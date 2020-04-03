package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class GetCategoryUseCaseImpl(val appDataBase: AppDataBase) : GetCategoryUseCase {

    override suspend fun getCategory(id: Long): ResultWrapper<Category> {
        return exequteRequest(Dispatchers.IO) {
            appDataBase.articleDao().getCategory(id)
        }
    }

}