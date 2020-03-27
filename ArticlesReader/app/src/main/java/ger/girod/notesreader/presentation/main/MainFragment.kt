package ger.girod.notesreader.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import ger.girod.notesreader.R
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.use_cases.DeleteArticleUseCaseImpl
import ger.girod.notesreader.domain.use_cases.GetArticlesUseCaseImpl
import ger.girod.notesreader.domain.use_cases.MarkArticleAsReadUseCaseImpl
import ger.girod.notesreader.domain.use_cases.SaveArticleUseCaseImpl
import ger.girod.notesreader.presentation.MyViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment(var articleLink: String) : Fragment(), ArticleAdapter.RowClick {

    companion object {
        fun newInstance(articleLink: String) = MainFragment(articleLink)
    }

    private lateinit var viewModel: MainViewModel
    private val adapter: ArticleAdapter by lazy { ArticleAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun initViewModel() {
        val appDataBase = AppDataBase.getDatabaseInstance()
        viewModel = ViewModelProviders.of(this, MyViewModelFactory {
            MainViewModel(
                GetArticlesUseCaseImpl(
                    appDataBase!!
                ), SaveArticleUseCaseImpl(appDataBase),
                MarkArticleAsReadUseCaseImpl(appDataBase),
                DeleteArticleUseCaseImpl(appDataBase)
            )
        })[MainViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()
        initList()
        getArticles()

        if (articleLink.isNotEmpty()) viewModel.getArticleFromIntent(articleLink)

        viewModel.articleData.observe(this, Observer { t ->
            if (t != null) {
                context?.let {
                    populateDialog(t)
                }

            }
        })

        viewModel.articlesData.observe(this, Observer { t ->
            if (t != null) {
                adapter.setList(t)
            }
        })

        viewModel.savedArticleData.observe(this, Observer { t ->
            if (t != null) {
                getArticles()
            }
        })

        viewModel.deleteArticlesData.observe(this, Observer { t ->
            adapter.deleteArtitle(t)
        })

        viewModel.updateArticleData.observe(this, Observer { t ->
            adapter.onMarkAsRead(t)
        })
    }

    private fun populateDialog(article: Article) {

        MaterialDialog(context!!).show {
            title(R.string.dialog_title_save)
            message(null, article.title)
            positiveButton(R.string.dialog_save) {
                viewModel.saveArticle(article)
            }
            negativeButton(R.string.dialog_cancel) {
                this.cancel()
            }
        }
    }

    private fun getArticles() {
        viewModel.getAllArticles()
    }

    private fun initList() {
        list.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(activity)
        list.layoutManager = layoutManager
        list.adapter = adapter
    }

    override fun onOpenArticle(article: Article) {
        activity!!.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.description)))
    }

    override fun onDeleteArticle(article: Article, position: Int) {
        MaterialDialog(context!!).show {
            title(R.string.dialog_title_remove)
            positiveButton(R.string.dialog_remove) {
                viewModel.deleteArticle(article, position)
            }
            negativeButton(R.string.dialog_cancel) {
                this.cancel()
            }
        }
    }

    override fun onMarkAsRead(article: Article, position: Int) {
        MaterialDialog(context!!).show {
            title(R.string.dialog_title_read)
            positiveButton(R.string.dialog_yes) {
                viewModel.markArticleAsRead(article, position)
            }
            negativeButton(R.string.dialog_cancel) {
                this.cancel()
            }
        }
    }

}
