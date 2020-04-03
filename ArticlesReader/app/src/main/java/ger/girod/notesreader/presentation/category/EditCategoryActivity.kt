package ger.girod.notesreader.presentation.category

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import ger.girod.notesreader.R
import ger.girod.notesreader.data.database.AppDataBase
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.domain.use_cases.*
import ger.girod.notesreader.presentation.MyViewModelFactory
import kotlinx.android.synthetic.main.category_activity.*
import kotlinx.android.synthetic.main.category_activity.category_name
import kotlinx.android.synthetic.main.category_activity.toolbar
import kotlinx.android.synthetic.main.category_bottom_sheet_row.*
import kotlinx.android.synthetic.main.edit_category_activity.*

const val CATEGORY_ID = "category_id"
class EditCategoryActivity : AppCompatActivity() {

    companion object {
        fun getIntent(context: Context, categoryId : Long) : Intent {
            return Intent(context, EditCategoryActivity::class.java).apply {
                putExtra(CATEGORY_ID, categoryId)
            }
        }
    }

    private lateinit var editCategoryViewModel: EditCategoryViewModel
    private lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_category_activity)

        initViewModel()

        editCategoryViewModel.getCategory(intent.getLongExtra(CATEGORY_ID, 0))

        editCategoryViewModel.categoryData.observe(this, Observer {
            category = it
            category_name.setText(category.name)
        })

        edit_category.setOnClickListener {
            if(editCategoryViewModel.isChangeValid(category_name.text.toString())) {
                category.name = category_name.text.toString()
                editCategoryViewModel.updateCategory(category)
                setResult(Activity.RESULT_OK, setResultIntent())
                finish()
            }
        }

        setSupportActionBar(toolbar)
        supportActionBar!!.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setTitle(R.string.edit_category)
        }
    }

    private fun initViewModel() {
        val appDataBase = AppDataBase.getDatabaseInstance()
        editCategoryViewModel = ViewModelProviders.of(this, MyViewModelFactory{
            EditCategoryViewModel(DeleteCategoryUseCaseImpl(appDataBase!!), UpdateCategoryUseCaseImpl(appDataBase),
                GetCategoryUseCaseImpl(appDataBase))
        })[EditCategoryViewModel::class.java]

    }

    private fun setResultIntent() : Intent {
        return Intent().apply {
            putExtra(CATEGORY_ID, category.id)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.edit_category_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item!!.itemId) {
            R.id.action_delete -> {
                editCategoryViewModel.deleteCategory(category)
                finish()
            }
            android.R.id.home -> {
                onBackPressed()
            }

        }
        return true
    }

}