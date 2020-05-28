package ger.girod.notesreader.presentation.main.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.R

import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ger.girod.notesreader.domain.entities.Category
import kotlinx.android.synthetic.main.bottom_dialog_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.collections.ArrayList

class CategoriesBottomSheetDialogFragment(val  listener: Listener) : BottomSheetDialogFragment() {

    private lateinit var viewManager : RecyclerView.LayoutManager
    private lateinit var categoriesBottomSheetAdapter: CategoriesBottomSheetAdapter
    private val bottomSheetViewModel: BottomSheetViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_dialog_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()

        bottomSheetViewModel.getCategoryList()
        bottomSheetViewModel.categoriesData.observe(viewLifecycleOwner, Observer {
            categoriesBottomSheetAdapter.setList(it as ArrayList<Category>)
        })
        bottomSheetViewModel.errorData.observe(viewLifecycleOwner, Observer {

        })

        create_category_container.setOnClickListener {
            listener.createNewCategory()
        }
    }

    private fun setupList() {

        viewManager = LinearLayoutManager(this.context)
        categoriesBottomSheetAdapter =
            CategoriesBottomSheetAdapter(
                listener
            )

        category_list.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = categoriesBottomSheetAdapter
        }

    }

    interface Listener {
        fun createNewCategory()

        fun showCategory(category: Category)
    }
}