package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category

interface SaveCategoryUseCase {

    suspend fun saveCategory(category : Category) : ResultWrapper<Unit>
}