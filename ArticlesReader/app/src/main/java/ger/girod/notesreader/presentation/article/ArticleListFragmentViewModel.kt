package ger.girod.notesreader.presentation.article

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ger.girod.notesreader.data.model.CategoriesAndArticle
import ger.girod.notesreader.data.model.ResultWrapper
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.util.*

class ArticleListFragmentViewModel(
    private val saveArticleUseCase: SaveArticleUseCase,
    private val markArticleAsReadUseCase: MarkArticleAsReadUseCase,
    private val deleteArticleUseCase: DeleteArticleUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getArticlesByCategoryUseCase: GetArticlesByCategoryUseCase,
    private val getCategoryUseCase: GetCategoryUseCase) : ViewModel() {


    var categoriesAndArticleData: MutableLiveData<CategoriesAndArticle> = MutableLiveData()
    var articlesData: MutableLiveData<List<Article>> = MutableLiveData()
    var deleteArticlesData: MutableLiveData<Int> = MutableLiveData()
    var updateArticleData: MutableLiveData<Int> = MutableLiveData()
    var savedArticleData: MutableLiveData<Unit> = MutableLiveData()
    var errorData: MutableLiveData<String> = MutableLiveData()
    var categoryData : MutableLiveData<Category> = MutableLiveData()

    fun testFirebase(article: Article) {

        viewModelScope.launch {
            val db = Firebase.firestore

            val saveArticle = hashMapOf(
                "title" to article.title,
                "description" to article.description
            )

           try{
                val document  = db
                    .collection("articles")
                    .add(saveArticle)
                    .await()

               Log.e("mirar aca ","mirar aca ${document.id}")
               Log.e("mirar aca ","mirar aca ${document.parent}")
               Log.e("mirar aca ","mirar aca ${document.path}")


            }catch (e : Exception){
                Log.e("mrar aca ","mirar aca "+e.localizedMessage)
            }
        }
    }

    fun getArticleFromIntent(link: String) {
        viewModelScope.launch {
            val document = withContext(Dispatchers.IO) {
               Jsoup.connect(link).get()
            }

            val article = Article(0, document.title(), link, false, Date(System.currentTimeMillis()), 0)
            when(val categories = getCategoriesUseCase.getCategories()) {
                is ResultWrapper.Success -> {
                    val categoriesAndArticle = CategoriesAndArticle(categories.value, article)
                    categoriesAndArticleData.postValue(categoriesAndArticle)
                }
            }
        }
    }

    fun getCategoryById(categoryId: Long) {
        viewModelScope.launch {
            when(val response = getCategoryUseCase.getCategory(categoryId)) {
                is ResultWrapper.Success -> categoryData.postValue(response.value)
                is ResultWrapper.Error -> errorData.postValue(response.exception.message)
            }
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

    fun getArticlesByCategory(categoryId : Long) {
        viewModelScope.launch {
            when(val response = getArticlesByCategoryUseCase.getArticlesByCategory(categoryId)) {
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
