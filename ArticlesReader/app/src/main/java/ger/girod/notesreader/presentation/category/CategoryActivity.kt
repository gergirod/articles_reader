package ger.girod.notesreader.presentation.category

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ger.girod.notesreader.R
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.domain.entities.Category
import kotlinx.android.synthetic.main.category_activity.*
import org.koin.androidx.viewmodel.ext.android.viewModel

const val CATEGORY = "category"
class CategoryActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, CategoryActivity::class.java)
        }

        fun getIntent(context: Context, article: Article) : Intent {
            return Intent(context, CategoryActivity::class.java).apply {
                putExtra(CATEGORY, article)
            }
        }
    }


    private var article: Article? = null

    private val createCategoryViewModel: CreateCategoryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)

        article = intent.extras?.getParcelable(CATEGORY)

        createCategoryViewModel.saveCategoryData.observe(this, Observer {
            createCategoryViewModel.saveLatestCategory(it)
            setResult(Activity.RESULT_OK, setResultIntent(it))
            finish()
        })

        setSupportActionBar(toolbar)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.create_category)
        }
    }

    private fun createCategory(title : String) : Category {
        return Category(0, title, "")
    }

    private fun setResultIntent(categoryId : Long) : Intent {
        return Intent().apply {
            putExtra(CATEGORY_ID, categoryId)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.action_save -> {
                createCategoryViewModel.saveCategory(createCategory(category_name.text.toString()),
                    article)
            }
            android.R.id.home -> {
                onBackPressed()
            }

        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}