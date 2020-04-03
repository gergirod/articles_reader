package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class GetArticlesByCategoryUseCaseImpl(val appDataBase: AppDataBase) : GetArticlesByCategoryUseCase {
    override suspend fun getArticlesByCategory(categoryId: Long) : ResultWrapper<List<Article>>{
        return exequteRequest(Dispatchers.IO){
            appDataBase.articleDao().getArticlesByCategoryId(categoryId)
        }

    }
}