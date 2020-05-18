package ger.girod.notesreader.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import ger.girod.notesreader.R
import ger.girod.notesreader.data.providers.PreferencesManager
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.presentation.article.ArticleListFragment
import ger.girod.notesreader.presentation.category.CATEGORY_DELETED
import ger.girod.notesreader.presentation.category.CATEGORY_ID
import ger.girod.notesreader.presentation.category.CategoryActivity
import ger.girod.notesreader.presentation.category.EditCategoryActivity
import ger.girod.notesreader.presentation.main.bottom_sheet.CategoriesBottomSheetDialogFragment
import kotlinx.android.synthetic.main.main_activity.*

const val CREATE_ACTIVITY_REQUEST_CODE: Int = 1
const val EDIT_ACTIVITY_REQUEST_CODE: Int = 2
const val RC_SIGN_IN = 123

class MainActivity : AppCompatActivity(), CategoriesBottomSheetDialogFragment.Listener {

    private var intentString: String = ""
    private lateinit var fragmentDialog: BottomSheetDialogFragment
    private lateinit var articleListFragment: ArticleListFragment
    private lateinit var category: Category
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        setSupportActionBar(bottom_bar)

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            showMainUi(savedInstanceState)
        } else {
            showLoginUI()
        }

        fab.setOnClickListener {
            startActivityForResult(
                EditCategoryActivity.getIntent(
                    this,
                    PreferencesManager.getInstance()!!.getLastCategorySelectedId()
                ), EDIT_ACTIVITY_REQUEST_CODE
            )
        }

    }

    private fun showMainUi(savedInstanceState: Bundle?) {
        when {
            intent.action == Intent.ACTION_SEND -> {
                if ("text/plain" == intent.type) {
                    intentString = intent.getStringExtra(Intent.EXTRA_TEXT)
                }
            }
        }

        if (savedInstanceState == null) {
            articleListFragment = ArticleListFragment.newInstance(intentString)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, articleListFragment)
                .commitNow()
        }
    }


    private fun showLoginUI() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setTheme(R.style.AppSignInTheme)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {

            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                Log.e("mirar aca ", "mirar aca ${user!!.providerData[0]?.displayName}")
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }

        } else if (requestCode == CREATE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                articleListFragment.populateListAndTitle(data!!.getLongExtra(CATEGORY_ID, 0))
            }
        } else {
            if (resultCode == Activity.RESULT_OK) {
                if (data!!.getBooleanExtra(CATEGORY_DELETED, false)) {
                    articleListFragment.populateListAndTitle(data!!.getLongExtra(CATEGORY_ID, 0))

                } else {
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
        PreferencesManager.getInstance()!!.saveLastCategorySelectedId(category.id)
        fragmentDialog.dismiss()
        articleListFragment.populateListAndTitle(category.id)

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
