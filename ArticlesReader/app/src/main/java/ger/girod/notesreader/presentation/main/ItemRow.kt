package ger.girod.notesreader.presentation.main

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.R
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.utils.DateUtils
import ger.girod.notesreader.utils.RowUtils
import kotlinx.android.synthetic.main.article_row.view.*

class ItemRow(itemView: View, private val rowClick: ArticleAdapter.RowClick) :
    RecyclerView.ViewHolder(itemView) {

    var context: Context = itemView.context

    fun populateArticle(article: Article, position: Int) {

        itemView.article_title.text = article.title
        itemView.date.text = DateUtils.toSimpleString(article.dateSaved)
        itemView.left_view.setBackgroundColor(context.resources.getColor(R.color.row_background))

        itemView.row_base_view.setBackgroundColor(
            RowUtils.getRowBackgrounColor(
                article.isRead,
                context
            )
        )

        val color = RowUtils.getTextAndIconColors(article.isRead, context)
        itemView.delete_icon.setColorFilter(color, android.graphics.PorterDuff.Mode.SRC_IN)
        itemView.check_icon.setColorFilter(
            RowUtils.getCheckIconColor(article.isRead, context),
            android.graphics.PorterDuff.Mode.SRC_IN
        )
        itemView.article_title.setTextColor(color)
        itemView.date.setTextColor(color)

        if (article.isRead) {
            itemView.check_icon.isEnabled = false
        }

        itemView.setOnClickListener {
            rowClick.onOpenArticle(article)
        }

        itemView.delete_icon.setOnClickListener {
            rowClick.onDeleteArticle(article, position)
        }

        itemView.check_icon.setOnClickListener {
            rowClick.onMarkAsRead(article, position)
        }
    }

}
