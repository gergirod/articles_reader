package ger.girod.notesreader.presentation.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.SaveArticleUseCase
import ger.girod.notesreader.domain.use_cases.SaveCategoryUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class CreateCategoryViewModel(private val saveCategoryUseCase: SaveCategoryUseCase,
                              private val saveArticleUseCase: SaveArticleUseCase) : ViewModel() , KoinComponent {

    var saveCategoryData : MutableLiveData<Long> = MutableLiveData()
    var errorData : MutableLiveData<String> = MutableLiveData()
    private val preferenceManager : PreferencesManager by inject()

    fun saveLatestCategory(id : Long) {
        preferenceManager.saveLastCategorySelectedId(id)
    }

    fun saveCategory(category : Category, article: Article?) {
        viewModelScope.launch {

            when(val response = saveCategoryUseCase.saveCategory(category)) {
                is ResultWrapper.Success -> {
                    if(article != null) {
                        article.categoryId = response.value
                        saveArticleUseCase.saveArticle(article)
                    }
                    saveCategoryData.postValue(response.value)
                }
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }
}