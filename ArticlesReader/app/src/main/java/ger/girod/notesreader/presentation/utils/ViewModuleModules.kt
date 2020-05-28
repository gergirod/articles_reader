package ger.girod.notesreader.presentation.utils

import ger.girod.notesreader.presentation.article.ArticleListFragmentViewModel
import ger.girod.notesreader.presentation.category.CreateCategoryViewModel
import ger.girod.notesreader.presentation.category.EditCategoryViewModel
import ger.girod.notesreader.presentation.main.bottom_sheet.BottomSheetViewModel
import org.koin.dsl.module

val viewModelModules = module {
    factory { BottomSheetViewModel(get()) }
    factory { EditCategoryViewModel(get(), get(), get(), get()) }
    factory { CreateCategoryViewModel(get(), get()) }
    factory { ArticleListFragmentViewModel(get(), get(), get(), get(), get(), get(), get()) }
}