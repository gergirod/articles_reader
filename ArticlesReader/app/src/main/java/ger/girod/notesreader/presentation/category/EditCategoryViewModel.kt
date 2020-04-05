package ger.girod.notesreader.presentation.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.*
import kotlinx.coroutines.launch

class EditCategoryViewModel(private val deleteCategoryUseCase: DeleteCategoryUseCase,
                            private val updateCategoryUseCase: UpdateCategoryUseCase,
                            private val getCategoryUseCase: GetCategoryUseCase,
                            private val getRandomCategoryUseCase: GetRandomCategoryUseCase) : ViewModel() {

    var categoryData : MutableLiveData<Category> = MutableLiveData()
    var deleteCategoryData : MutableLiveData<Long> = MutableLiveData()
    var updateCategoryData : MutableLiveData<Unit> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    private suspend fun getRandomCategory() {

        when(val response = getRandomCategoryUseCase.getRadomCategory()) {
            is ResultWrapper.Success -> deleteCategoryData.postValue(response.value.id)
            is ResultWrapper.Error -> errorData.postValue(response.exception.message)
        }
    }

    fun deleteCategory(category : Category) {
        viewModelScope.launch {
            when(val response = deleteCategoryUseCase.deleteCategory(category)) {
                is ResultWrapper.Success -> {
                    getRandomCategory()
                }
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }

    fun saveLatestCategory(id : Long) {
        PreferencesManager.getInstance()!!.saveLastCategorySelectedId(id)
    }


    fun isChangeValid(categoryName: String) : Boolean {

        if(categoryName.isNotEmpty()) {
            return true
        }
        return false
    }

    fun updateCategory(category: Category) {
        viewModelScope.launch {
            when(val response = updateCategoryUseCase.updateCategory(category)) {
                is ResultWrapper.Success -> updateCategoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }

    fun getCategory(categoryId : Long) {
        viewModelScope.launch {
            when(val response = getCategoryUseCase.getCategory(categoryId)) {
                is ResultWrapper.Success -> categoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }
}