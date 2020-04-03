package ger.girod.notesreader.presentation.main.bottom_sheet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.domain.entities.Category
import ger.girod.notesreader.R
import kotlinx.android.synthetic.main.category_selector_dialog_row.view.*


class CategoriesSelectorAdapter() : RecyclerView.Adapter<CategoriesSelectorAdapter.CategoryItemRow>() {

    private var categorieList : ArrayList<Category> = ArrayList<Category>()
    var lastcheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryItemRow {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_selector_dialog_row,
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
        holder.populateContent(category, lastcheckedPosition)
        holder.view.category_name_radio_button.setOnClickListener {
            lastcheckedPosition = position
            notifyDataSetChanged()
        }
    }

     fun setList(categoryList : ArrayList<Category>) {
        this.categorieList.clear()
        this.categorieList.addAll(categoryList)
        notifyDataSetChanged()
    }


    class CategoryItemRow(val view : View) : RecyclerView.ViewHolder(view){

        fun populateContent(category: Category, lastCheckedPosition : Int) {
            view.category_name_radio_button.text = category.name
            view.category_name_radio_button.isChecked = adapterPosition == lastCheckedPosition
        }

    }

    public fun getCategoryIdByPosition() : Long {
        return categorieList[lastcheckedPosition].id
    }
}