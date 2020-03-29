package ger.girod.notesreader.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.DeleteCategoryUseCase
import ger.girod.notesreader.domain.use_cases.SaveCategoryUseCase
import ger.girod.notesreader.domain.use_cases.UpdateCategoryUseCase
import kotlinx.coroutines.launch

class EditCategoryViewModel(private val deleteCategoryUseCase: DeleteCategoryUseCase,
                            private val updateCategoryUseCase: UpdateCategoryUseCase) : ViewModel() {

    var deleteCategoryData : MutableLiveData<Unit> = MutableLiveData()
    var updateCategoryData : MutableLiveData<Unit> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    fun deleteCategory(category : Category) {
        viewModelScope.launch {
            when(val response = deleteCategoryUseCase.deleteCategory(category)) {
                is ResultWrapper.Success -> deleteCategoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            when(val response = updateCategoryUseCase.updateCategory(category)) {
                is ResultWrapper.Success -> updateCategoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }
}