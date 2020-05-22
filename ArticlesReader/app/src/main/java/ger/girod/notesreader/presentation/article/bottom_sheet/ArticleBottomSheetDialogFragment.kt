package ger.girod.notesreader.presentation.article.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ger.girod.notesreader.R
import ger.girod.notesreader.domain.entities.Article
import kotlinx.android.synthetic.main.article_bottom_sheet_fragment.*

class ArticleBottomSheetDialogFragment(private val article: Article, private val position: Int,
                                       private  val  listener: Listener
) : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.article_bottom_sheet_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remove_container.setOnClickListener {
            listener.onRemoveArticle(article, position)
        }

        move_to_container.setOnClickListener {
            listener.onMoveToArticle(article, position)
        }
    }


    interface Listener {

        fun onRemoveArticle(article: Article, position: Int)

        fun onMoveToArticle(article: Article, position: Int)

    }
}