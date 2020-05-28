package ger.girod.notesreader.presentation.main

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.transition.MaterialContainerTransformSharedElementCallback
import ger.girod.notesreader.R
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.presentation.article.ArticleListFragment
import ger.girod.notesreader.presentation.category.CATEGORY_DELETED
import ger.girod.notesreader.presentation.category.CATEGORY_ID
import ger.girod.notesreader.presentation.category.CategoryActivity
import ger.girod.notesreader.presentation.category.EditCategoryActivity
import ger.girod.notesreader.presentation.main.bottom_sheet.CategoriesBottomSheetDialogFragment
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.core.KoinComponent
import org.koin.core.inject

const val CREATE_ACTIVITY_REQUEST_CODE : Int = 1
const val EDIT_ACTIVITY_REQUEST_CODE : Int = 2


class MainActivity : AppCompatActivity(), CategoriesBottomSheetDialogFragment.Listener ,
    ArticleListFragment.Listener, KoinComponent{

    private var intentString: String = ""
    private lateinit var fragmentDialog : BottomSheetDialogFragment
    private lateinit var articleListFragment : ArticleListFragment
    private lateinit var category: Category
    private val preferenceManager : PreferencesManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(bottom_bar)
        when {
            intent.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intentString = intent.getStringExtra(Intent.EXTRA_TEXT)
                }
            }
        }

        if (savedInstanceState == null) {
            articleListFragment = ArticleListFragment.newInstance(intentString)
            articleListFragment.setListener(this)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, articleListFragment)
                .commitNow()
        }

        fab.setOnClickListener {
            startActivityForResult(EditCategoryActivity.getIntent(this,
                preferenceManager.getLastCategorySelectedId()), EDIT_ACTIVITY_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == CREATE_ACTIVITY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                articleListFragment.populateListAndTitle(data!!.getLongExtra(CATEGORY_ID, 0))
            }
        }else {
            if(resultCode == Activity.RESULT_OK) {
                if(data!!.getBooleanExtra(CATEGORY_DELETED, false)) {
                    articleListFragment.populateListAndTitle(data!!.getLongExtra(CATEGORY_ID, 0))

                }else{
                    articleListFragment.populateTitle(data!!.getLongExtra(CATEGORY_ID, 0))
                }
            }
        }

    }

    override fun createNewCategory() {
        fragmentDialog.dismiss()
        startActivityForResult(CategoryActivity.getIntent(this), CREATE_ACTIVITY_REQUEST_CODE)
    }

    override fun showCategory(category: Category) {
        this.category = category
        preferenceManager.saveLastCategorySelectedId(category.id)
        fragmentDialog.dismiss()
        articleListFragment.populateListAndTitle(category.id)

    }

    override fun onChangeCategory(article: Article) {
        val categoryId = article.categoryId
        preferenceManager.saveLastCategorySelectedId(categoryId)
        articleListFragment.populateListAndTitle(categoryId)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottom_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu -> {
                fragmentDialog =
                    CategoriesBottomSheetDialogFragment(
                        this
                    )
                fragmentDialog.show(supportFragmentManager, fragmentDialog.tag)
            }

        }
        return true
    }
}
