package org.lds.ldssa.model.database.search.searchsuggestion;

import org.lds.ldssa.inject.CommonMockTestModule;
import org.lds.ldssa.inject.CommonTestModule;
import org.lds.ldssa.task.AnnotationFixTaskTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {CommonTestModule.class, CommonMockTestModule.class, SearchSuggestionManagerTestModule.class})
public interface SearchSuggestionManagerTestComponent {
    void inject(AnnotationFixTaskTest target);

    void inject(SearchSuggestionManagerTest target);
}