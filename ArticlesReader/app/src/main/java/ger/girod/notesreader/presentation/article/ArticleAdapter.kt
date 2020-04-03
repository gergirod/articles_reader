package ger.girod.notesreader.presentation.article

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ger.girod.notesreader.R
import ger.girod.notesreader.domain.entities.Article

class ArticleAdapter(private val rowClick: RowClick) : RecyclerView.Adapter<ItemRow>() {

    private var articles: ArrayList<Article> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRow {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.article_row, parent, false)
        return ItemRow(view, rowClick)
    }

    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: ItemRow, position: Int) {
        val article = articles[position]
        holder.populateArticle(article, position)
    }

    fun deleteArtitle(position: Int) {
        articles.removeAt(position)
        notifyDataSetChanged()
    }

    fun onMarkAsRead(position: Int) {
        val article = articles[position]
        article.isRead = true
        articles[position] = article
        notifyItemChanged(position)
    }

    fun setList(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    interface RowClick {

        fun onOpenArticle(article: Article)

        fun onDeleteArticle(article: Article, position: Int)

        fun onMarkAsRead(article: Article, position: Int)

    }
}