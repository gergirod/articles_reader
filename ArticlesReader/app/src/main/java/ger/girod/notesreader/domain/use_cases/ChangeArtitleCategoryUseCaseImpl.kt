package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.exequteRequest
import kotlinx.coroutines.Dispatchers

class ChangeArtitleCategoryUseCaseImpl(private val articleDao: ArticleDao) : ChangeArtitleCategoryUseCase {

    override suspend fun changeArticleCategory(article: Article): ResultWrapper<Unit> {
        return exequteRequest(Dispatchers.IO) {
            articleDao.updateArticle(article)
        }
    }
}