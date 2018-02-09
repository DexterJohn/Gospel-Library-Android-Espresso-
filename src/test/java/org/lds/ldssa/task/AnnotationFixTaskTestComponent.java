package org.lds.ldssa.task;

import org.lds.ldssa.inject.CommonMockTestModule;
import org.lds.ldssa.inject.CommonTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CommonTestModule.class,
        CommonMockTestModule.class,
        AnnotationFixTaskTestModule.class
})
public interface AnnotationFixTaskTestComponent {
    void inject(AnnotationFixTaskTest target);
}