package org.lds.ldssa.ux

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.lds.ldssa.ux.annotations.AnnotationsViewModel
import org.lds.ldssa.ux.annotations.allannotations.AllAnnotationsViewModel
import org.lds.ldssa.ux.annotations.links.LinksViewModel
import org.lds.ldssa.ux.annotations.notebooks.NotebooksViewModel
import org.lds.ldssa.ux.annotations.notebookselection.NotebookSelectionViewModel
import org.lds.ldssa.ux.annotations.tags.TagsViewModel
import org.lds.ldssa.ux.annotations.tagselection.TagSelectionViewModel
import org.lds.ldssa.ux.catalog.CatalogDirectoryViewModel
import org.lds.ldssa.ux.content.directory.ContentDirectoryViewModel
import org.lds.ldssa.ux.customcollection.collections.CustomCollectionsViewModel
import org.lds.ldssa.ux.customcollection.items.CustomCollectionDirectoryViewModel
import org.lds.ldssa.ux.downloadedmedia.DownloadedMediaViewModel
import org.lds.ldssa.ux.locations.bookmarks.BookmarksViewModel
import org.lds.ldssa.ux.study.items.StudyItemsViewModel
import org.lds.ldssa.ux.study.plans.StudyPlansViewModel
import org.lds.ldssa.ux.tips.lists.TipListPagerViewModel
import org.lds.ldssa.ux.tips.lists.listitems.TipListViewModel
import org.lds.ldssa.ux.tips.pages.TipsPagerViewModel
import org.lds.ldssa.ux.tips.pages.tip.TipViewModel
import org.lds.ldssa.ux.welcome.WelcomeViewModel
import org.lds.mobile.inject.viewmodel.ViewModelFactory
import org.lds.mobile.inject.viewmodel.ViewModelKey

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(CatalogDirectoryViewModel::class)
    internal abstract fun bindCatalogDirectoryViewModel(viewModel: CatalogDirectoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomCollectionsViewModel::class)
    internal abstract fun bindCustomCollectionsViewModel(viewModel: CustomCollectionsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CustomCollectionDirectoryViewModel::class)
    internal abstract fun bindCustomCollectionDirectoryViewModel(viewModel: CustomCollectionDirectoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContentDirectoryViewModel::class)
    internal abstract fun bindContentDirectoryViewModel(viewModel: ContentDirectoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllAnnotationsViewModel::class)
    internal abstract fun bindAllAnnotationsViewModel(viewModel: AllAnnotationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AnnotationsViewModel::class)
    internal abstract fun bindAnnotationsViewModel(viewModel: AnnotationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TagSelectionViewModel::class)
    internal abstract fun bindTagSelectionViewModel(viewModel: TagSelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TagsViewModel::class)
    internal abstract fun bindTagsViewModel(viewModel: TagsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotebookSelectionViewModel::class)
    internal abstract fun bindNotebookSelectionViewModel(viewModel: NotebookSelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NotebooksViewModel::class)
    internal abstract fun bindNotebooksViewModel(viewModel: NotebooksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookmarksViewModel::class)
    internal abstract fun bindBookmarksViewModel(viewModel: BookmarksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LinksViewModel::class)
    internal abstract fun bindLinksViewModel(viewModel: LinksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DownloadedMediaViewModel::class)
    internal abstract fun bindDownloadedMediaViewModel(viewModel: DownloadedMediaViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StudyPlansViewModel::class)
    internal abstract fun bindStudyPlansViewModel(viewModel: StudyPlansViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(StudyItemsViewModel::class)
    internal abstract fun bindStudyItemsViewModel(viewModel: StudyItemsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TipViewModel::class)
    internal abstract fun bindTipViewModel(viewModel: TipViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TipsPagerViewModel::class)
    internal abstract fun bindTipItemsViewModel(viewModel: TipsPagerViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WelcomeViewModel::class)
    internal abstract fun bindWelcomeViewModel(viewModel: WelcomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TipListViewModel::class)
    internal abstract fun bindTipListViewModel(viewModel: TipListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TipListPagerViewModel::class)
    internal abstract fun bindTipListPagerViewModel(viewModel: TipListPagerViewModel): ViewModel

}
