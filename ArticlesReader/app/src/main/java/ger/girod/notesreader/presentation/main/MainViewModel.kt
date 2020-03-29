package ger.girod.notesreader.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.use_cases.DeleteArticleUseCase
import ger.girod.notesreader.domain.use_cases.GetArticlesUseCase
import ger.girod.notesreader.domain.use_cases.MarkArticleAsReadUseCase
import ger.girod.notesreader.domain.use_cases.SaveArticleUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.*

class MainViewModel(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val saveArticleUseCase: SaveArticleUseCase,
    private val markArticleAsReadUseCase: MarkArticleAsReadUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase) : ViewModel() {


    var articleData: MutableLiveData<Article> = MutableLiveData()
    var articlesData: MutableLiveData<List<Article>> = MutableLiveData()
    var deleteArticlesData: MutableLiveData<Int> = MutableLiveData()
    var updateArticleData: MutableLiveData<Int> = MutableLiveData()
    var savedArticleData: MutableLiveData<Unit> = MutableLiveData()
    var errorData: MutableLiveData<String> = MutableLiveData()


    fun getArticleFromIntent(link: String) {
        viewModelScope.launch {
            val document = withContext(Dispatchers.IO) {
                Jsoup.connect(link).get()
            }
            val article =
                Article(0, document.title(), link, false, Date(System.currentTimeMillis()), 0)
            articleData.value = article
        }
    }

    fun saveArticle(article: Article) {
        viewModelScope.launch {
            when (val response = saveArticleUseCase.saveArticle(article)) {
                is ResultWrapper.Success -> savedArticleData.value = response.value
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }

    fun getAllArticles() {
        viewModelScope.launch {
            when (val response = getArticlesUseCase.getAllArticles()) {
                is ResultWrapper.Success -> articlesData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
        }
    }

    fun deleteArticle(article: Article, position: Int) {
        viewModelScope.launch {
            when (val response = deleteArticleUseCase.deleteArticle(article)) {
                is ResultWrapper.Success -> deleteArticlesData.value = position
                is ResultWrapper.Error -> response.exception.message
            }
        }
    }

    fun markArticleAsRead(article: Article, position: Int) {
        article.isRead = true
        viewModelScope.launch {
            when (val response = markArticleAsReadUseCase.markArticleAsRead(article)) {
                is ResultWrapper.Success -> updateArticleData.value = position
                is ResultWrapper.Error -> response.exception.message
            }
        }
    }

}
