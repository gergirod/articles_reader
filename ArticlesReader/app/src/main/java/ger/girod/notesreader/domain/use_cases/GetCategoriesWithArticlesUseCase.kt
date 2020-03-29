package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.CategoryWithArticles
import ger.girod.notesreader.data.model.ResultWrapper

interface GetCategoriesWithArticlesUseCase {

    suspend fun getCategoriesWithArtiles() : ResultWrapper<List<CategoryWithArticles>>
}