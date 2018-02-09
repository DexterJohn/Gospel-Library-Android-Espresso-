package org.lds.ldssa.util;

import org.lds.ldssa.inject.CommonMockTestModule;
import org.lds.ldssa.inject.CommonTestModule;
import org.lds.ldssa.inject.DbSampleCatalogTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CommonTestModule.class,
        CommonMockTestModule.class,
        DbSampleCatalogTestModule.class,
        UriUtilTest.UriUtilTestModule.class})
public interface UriUtilTestComponent {
    void inject(UriUtilTest target);
}