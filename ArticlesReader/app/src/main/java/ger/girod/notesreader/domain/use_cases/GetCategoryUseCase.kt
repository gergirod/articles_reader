package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category

interface GetCategoryUseCase {

    suspend fun getCategory(id : Long) : ResultWrapper<Category>
}