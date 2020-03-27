package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class DeleteArticleUseCaseImpl(private val dataBase: AppDataBase) : DeleteArticleUseCase {

    override suspend fun deleteArticle(article: Article): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            dataBase.articleDao().deleteArticle(article)
        }
    }
}