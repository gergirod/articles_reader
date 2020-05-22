package ger.girod.notesreader.presentation.article

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
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.list.customListAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import ger.girod.notesreader.R
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.model.CategoriesAndArticle
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.*
import ger.girod.notesreader.presentation.utils.MyViewModelFactory
import ger.girod.notesreader.presentation.article.bottom_sheet.ArticleBottomSheetDialogFragment
import ger.girod.notesreader.presentation.main.bottom_sheet.CategoriesSelectorAdapter
import ger.girod.notesreader.utils.CategoryUtils
import ger.girod.notesreader.presentation.category.CategoryActivity
import ger.girod.notesreader.presentation.main.CREATE_ACTIVITY_REQUEST_CODE
import kotlinx.android.synthetic.main.custom_dialog_layout.*
import kotlinx.android.synthetic.main.main_fragment.*

const val ARTICLE_LINK = "article_link"
class ArticleListFragment : Fragment(), ArticleAdapter.RowClick, ArticleBottomSheetDialogFragment.Listener {

    private lateinit var articleLink : String
    private lateinit var fragmentDialog : BottomSheetDialogFragment
    private lateinit var article: Article
    private  var position: Int = -1
    private lateinit var listener: Listener
    companion object {
        fun newInstance(articleLink: String) = ArticleListFragment().apply {
            arguments = Bundle().apply {
                putString(ARTICLE_LINK, articleLink)
            }
        }
    }

    private val categorySelectorAdapter : CategoriesSelectorAdapter by lazy {
        CategoriesSelectorAdapter()
    }

    private lateinit var viewModel: ArticleListFragmentViewModel
    private val adapter: ArticleAdapter by lazy {
        ArticleAdapter(
            this
        )
    }

    fun setListener(listener: Listener ) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun initViewModel() {
        val appDataBase = AppDataBase.getDatabaseInstance()
        viewModel = ViewModelProviders.of(this,
            MyViewModelFactory {
                ArticleListFragmentViewModel(
                    SaveArticleUseCaseImpl(appDataBase!!),
                    MarkArticleAsReadUseCaseImpl(appDataBase),
                    DeleteArticleUseCaseImpl(appDataBase),
                    GetCategoriesUseCaseImpl(appDataBase),
                    GetArticlesByCategoryUseCaseImpl(appDataBase),
                    GetCategoryUseCaseImpl(appDataBase),
                    ChangeArtitleCategoryUseCaseImpl(appDataBase)
                )
            })[ArticleListFragmentViewModel::class.java]
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        articleLink = arguments!!.getString(ARTICLE_LINK, null)
        initViewModel()
        initList()
        populateListAndTitle(PreferencesManager.getInstance()!!.getLastCategorySelectedId())

        if (articleLink.isNotEmpty()) viewModel.getArticleFromIntent(articleLink)

        viewModel.categoriesAndArticleData.observe(this, Observer { t ->
            if (t != null) {
                context?.let {
                    populateDialog(t)
                }

            }
        })

        viewModel.errorData.observe(this, Observer {
            Snackbar.make(main, it, Snackbar.LENGTH_LONG).show()
        })

        viewModel.articlesData.observe(this, Observer { t ->
            if (t != null) {
                adapter.setList(t)
            }
        })

        viewModel.savedArticleData.observe(this, Observer { t ->
            if (t != null) {
                getArticlesByCategoryId(PreferencesManager.getInstance()!!.getLastCategorySelectedId())
            }
        })

        viewModel.deleteArticlesData.observe(this, Observer { t ->
            adapter.deleteArtitle(t)
        })

        viewModel.updateArticleData.observe(this, Observer { t ->
            adapter.onMarkAsRead(t)
        })

        viewModel.categoryData.observe(this, Observer {
            category_title.text = it.name
        })

        viewModel.categoriesMoveData.observe(this, Observer {
            populateChangeCategoryDialog(it)
        })

        viewModel.changeCategorySuccessData.observe(this , Observer {
            listener.onChangeCategory(it)
        })
    }


    fun populateListAndTitle(id : Long) {
        populateTitle(id)
        getArticlesByCategoryId(id)
    }

    fun populateTitle(id : Long) {
        getCategory(id)
    }
    private fun populateDialog(categoriesAndArticle: CategoriesAndArticle) {
        categorySelectorAdapter.setList(categoriesAndArticle.categories as ArrayList<Category>)
        MaterialDialog(context!!).show {
            cancelOnTouchOutside(false)
            title(R.string.dialog_title_save)
            message(null, categoriesAndArticle.article.title)
            //listItemsSingleChoice(items = myItems, initialSelection = 0)
            customListAdapter(categorySelectorAdapter)
            customView(R.layout.custom_dialog_layout)
            create_category_container.setOnClickListener {
                goToCreateCategory(categoriesAndArticle.article)
                dismiss()
            }
            positiveButton(R.string.dialog_save) {
                categoriesAndArticle.article.categoryId = categorySelectorAdapter.getCategoryIdByPosition()
                viewModel.saveArticle(categoriesAndArticle.article)
            }
            negativeButton(R.string.dialog_cancel) {
                this.cancel()
            }
        }
    }

    private fun goToCreateCategory(article: Article) {
        activity?.startActivityForResult(CategoryActivity.getIntent(activity!!, article), CREATE_ACTIVITY_REQUEST_CODE)
    }

    private fun getArticlesByCategoryId(categoryId : Long) {
        viewModel.getArticlesByCategory(categoryId)
    }

    private fun getCategory(categoryId: Long) {
        viewModel.getCategoryById(categoryId)
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
        fragmentDialog =
            ArticleBottomSheetDialogFragment(
                article,
                position,
                this
            )
        fragmentDialog.show(fragmentManager!!, fragmentDialog.tag)
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

    override fun onRemoveArticle(article: Article, position: Int) {
        MaterialDialog(context!!).show {
            title(R.string.dialog_title_remove)
            positiveButton(R.string.dialog_remove) {
                fragmentDialog.dismiss()
                viewModel.deleteArticle(article, position)
            }
            negativeButton(R.string.dialog_cancel) {
                fragmentDialog.dismiss()
                this.cancel()
            }
        }
    }

    private fun populateChangeCategoryDialog(categories : List<Category>) {
        categorySelectorAdapter.lastcheckedPosition = CategoryUtils.getCategoryPositionInList(article, categories)
        categorySelectorAdapter.setList(categories as ArrayList<Category>)
        MaterialDialog(context!!).show {
            cancelOnTouchOutside(false)
            title(R.string.move_to)
            customListAdapter(categorySelectorAdapter)
            positiveButton(R.string.move) {
                article.categoryId = categorySelectorAdapter.getCategoryIdByPosition()
                viewModel.changeArticleCategory(article, position)
                fragmentDialog.dismiss()
            }
            negativeButton(R.string.dialog_cancel) {
                this.cancel()
            }
        }
    }

    override fun onMoveToArticle(article: Article, position: Int) {
        this.position = position
        this.article = article
        viewModel.getCategoriesToMoveOption(article)
    }

    interface Listener {

        fun onChangeCategory(article: Article)

    }

}
