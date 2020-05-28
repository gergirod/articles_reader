package ger.girod.notesreader.presentation.main.bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.R
import ger.girod.notesreader.data.providers.PreferencesManager
import kotlinx.android.synthetic.main.category_bottom_sheet_row.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject


class CategoriesBottomSheetAdapter(val listener: CategoriesBottomSheetDialogFragment.Listener)
    : RecyclerView.Adapter<CategoriesBottomSheetAdapter.CategoryItemRow>(), KoinComponent {

    private var categorieList : ArrayList<Category> = ArrayList<Category>()
    private val preferencesManager : PreferencesManager by  inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemRow {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_bottom_sheet_row,
            parent, false)
        return CategoryItemRow(view, preferencesManager)
    }

    override fun getItemCount(): Int {
        return categorieList.size
    }

    override fun onBindViewHolder(holder: CategoryItemRow, position: Int) {
        val category = categorieList[position]
        holder.populateContent(category)
        holder.view.setOnClickListener {
            listener.showCategory(category)
        }
    }

     fun setList(categoryList : ArrayList<Category>) {
        this.categorieList.clear()
        this.categorieList.addAll(categoryList)
        notifyDataSetChanged()
    }


    class CategoryItemRow(val view : View , private val preferencesManager: PreferencesManager) : RecyclerView.ViewHolder(view){

        fun populateContent(category: Category) {
            val categoryId = preferencesManager.getLastCategorySelectedId()

            if(category.id == categoryId) {
                view.backgroundTintList = view.context.resources.getColorStateList(R.color.blue_dark)
                view.category_name.setTextColor(view.context.resources.getColor(R.color.white))
            }
            view.category_name.text = category.name
        }

    }
}