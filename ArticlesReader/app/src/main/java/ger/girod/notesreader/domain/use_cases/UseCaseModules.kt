package ger.girod.notesreader.domain.use_cases

import ger.girod.notesreader.data.daos.ArticleDao
import org.koin.dsl.module

fun provideChangeArticleUseCase(articleDao: ArticleDao) : ChangeArtitleCategoryUseCase {
    return ChangeArtitleCategoryUseCaseImpl(articleDao)
}

fun provideDeleteArticleUseCase(articleDao: ArticleDao) : DeleteArticleUseCase {
    return DeleteArticleUseCaseImpl(articleDao)
}

fun provideDeleteCategoryUseCase(articleDao: ArticleDao) : DeleteCategoryUseCase {
    return DeleteCategoryUseCaseImpl(articleDao)
}

fun provideGetArticleByCategoryUseCase(articleDao: ArticleDao) : GetArticlesByCategoryUseCase {
    return  GetArticlesByCategoryUseCaseImpl(articleDao)
}

fun provideGetCategoriesUseCase(articleDao: ArticleDao) : GetCategoriesUseCase {
    return  GetCategoriesUseCaseImpl(articleDao)
}

fun provideGetCategoriesWithArticlesUseCase(articleDao: ArticleDao) : GetCategoriesWithArticlesUseCase {
    return GetCategoriesWithArticlesUseCaseImpl(articleDao)
}

fun provideGetCategoryUseCase(articleDao: ArticleDao) : GetCategoryUseCase {
    return GetCategoryUseCaseImpl(articleDao)
}

fun provideGetRandomCategoryUseCase(articleDao: ArticleDao) : GetRandomCategoryUseCase {
    return GetRadomCategoryUseCaseImpl(articleDao)
}

fun provideMarkArticleAsReadUseCase(articleDao: ArticleDao) : MarkArticleAsReadUseCase {
    return MarkArticleAsReadUseCaseImpl(articleDao)
}

fun provideSaveArticleUseCase(articleDao: ArticleDao) : SaveArticleUseCase {
    return SaveArticleUseCaseImpl(articleDao)
}

fun provideSaveCategoryUseCase(articleDao: ArticleDao) : SaveCategoryUseCase {
    return SaveCategoryUseCaseImpl(articleDao)
}

fun provideUpdateCategoryUseCase(articleDao: ArticleDao) : UpdateCategoryUseCase {
    return UpdateCategoryUseCaseImpl(articleDao)
}

val useCaseModules = module {

    factory { provideChangeArticleUseCase(get()) }
    factory { provideDeleteArticleUseCase(get())}
    factory { provideDeleteCategoryUseCase(get()) }
    factory { provideGetArticleByCategoryUseCase(get()) }
    factory { provideGetCategoriesUseCase(get()) }
    factory { provideGetCategoriesWithArticlesUseCase(get()) }
    factory { provideGetCategoryUseCase(get()) }
    factory { provideGetRandomCategoryUseCase(get()) }
    factory { provideMarkArticleAsReadUseCase(get()) }
    factory { provideSaveArticleUseCase(get()) }
    factory { provideSaveCategoryUseCase(get()) }
    factory { provideUpdateCategoryUseCase(get()) }

}