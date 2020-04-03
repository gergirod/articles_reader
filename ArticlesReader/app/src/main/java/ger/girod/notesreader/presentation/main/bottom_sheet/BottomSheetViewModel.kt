package ger.girod.notesreader.presentation.main.bottom_sheet

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.GetCategoriesUseCase
import kotlinx.coroutines.launch

class BottomSheetViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) :ViewModel() {

    var categoriesData : MutableLiveData<List<Category>> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()

    fun getCategoryList() {

        viewModelScope.launch {
            when(val response = getCategoriesUseCase.getCategories()) {
                is ResultWrapper.Success -> categoriesData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}