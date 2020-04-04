package ger.girod.notesreader.presentation.category

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import ger.girod.notesreader.R
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.SaveCategoryUseCaseImpl
import ger.girod.notesreader.presentation.MyViewModelFactory
import kotlinx.android.synthetic.main.category_activity.*

class CategoryActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context) : Intent {
            return Intent(context, CategoryActivity::class.java)
        }
    }

    private lateinit var createCategoryViewModel: CreateCategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.category_activity)

        initViewModel()

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

    private fun initViewModel() {
        val appDataBase = AppDataBase.getDatabaseInstance()
        createCategoryViewModel = ViewModelProviders.of(this, MyViewModelFactory{
            CreateCategoryViewModel(SaveCategoryUseCaseImpl(appDataBase!!))
        })[CreateCategoryViewModel::class.java]

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
                createCategoryViewModel.saveCategory(createCategory(category_name.text.toString()))
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