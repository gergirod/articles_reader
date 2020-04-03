package ger.girod.notesreader.presentation.article

import android.content.Context
import android.inputmethodservice.Keyboard
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.R
import ger.girod.notesreader.domain.entities.Article
import ger.girod.notesreader.presentation.article.ArticleAdapter
import ger.girod.notesreader.utils.DateUtils
import ger.girod.notesreader.utils.RowUtils
import kotlinx.android.synthetic.main.article_row.view.*

class ItemRow(itemView: View, private val rowClick: ArticleAdapter.RowClick) :
    RecyclerView.ViewHolder(itemView) {

    var context: Context = itemView.context

    fun populateArticle(article: Article, position: Int) {

        itemView.article_title.text = article.title
        itemView.date.text = DateUtils.toSimpleString(article.dateSaved)

        itemView.card.setCardBackgroundColor(
            RowUtils.getBackgrounColor(
                article.isRead,
                context
            )
        )


        itemView.left_view.backgroundTintList = RowUtils.getSecondaryBackgroun(article.isRead, context)


        val textColor = RowUtils.getTextAndIconColors(article.isRead, context)
        itemView.delete_icon.setColorFilter(textColor, android.graphics.PorterDuff.Mode.SRC_IN)
        itemView.article_title.setTextColor(textColor)
        itemView.date.setTextColor(textColor)

        itemView.check_icon.setColorFilter(RowUtils.getCheckIconColor(article.isRead, context), android.graphics.PorterDuff.Mode.SRC_IN)

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
