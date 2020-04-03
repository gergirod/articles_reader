package ger.girod.notesreader.presentation.main.bottom_sheet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.R
import ger.girod.notesreader.data.providers.PreferencesManager
import kotlinx.android.synthetic.main.category_bottom_sheet_row.view.*


class CategoriesBottomSheetAdapter(val listener: CategoriesBottomSheetDialogFragment.Listener) : RecyclerView.Adapter<CategoriesBottomSheetAdapter.CategoryItemRow>() {

    private var categorieList : ArrayList<Category> = ArrayList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemRow {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_bottom_sheet_row,
            parent, false)
        return CategoryItemRow(
            view
        )
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


    class CategoryItemRow(val view : View) : RecyclerView.ViewHolder(view){

        fun populateContent(category: Category) {
            val categoryId = PreferencesManager.getInstance()!!.getLastCategorySelectedId()

            if(category.id == categoryId) {
                view.backgroundTintList = view.context.resources.getColorStateList(R.color.row_background)
                view.category_name.setTextColor(view.context.resources.getColor(R.color.white))
            }
            view.category_name.text = category.name
        }

    }
}