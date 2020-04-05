package ger.girod.notesreader.presentation.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.SaveCategoryUseCase
import kotlinx.coroutines.launch

class CreateCategoryViewModel(private val saveCategoryUseCase: SaveCategoryUseCase) : ViewModel() {

    var saveCategoryData : MutableLiveData<Long> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    fun saveLatestCategory(id : Long) {
        PreferencesManager.getInstance()!!.saveLastCategorySelectedId(id)
    }

    fun saveCategory(category : Category) {
        viewModelScope.launch {
            when(val response = saveCategoryUseCase.saveCategory(category)) {
                is ResultWrapper.Success -> saveCategoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }
}