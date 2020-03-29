package ger.girod.notesreader.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.SaveCategoryUseCase
import kotlinx.coroutines.launch

class CreateCategoryViewModel(private val saveCategoryUseCase: SaveCategoryUseCase) : ViewModel() {

    var saveCategoryData : MutableLiveData<Unit> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    fun saveCategory(category : Category) {

        viewModelScope.launch {
            when(val response = saveCategoryUseCase.saveCategory(category)) {
                is ResultWrapper.Success -> saveCategoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }

    }
}