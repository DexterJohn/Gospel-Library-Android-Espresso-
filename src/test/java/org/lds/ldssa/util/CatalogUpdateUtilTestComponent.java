package org.lds.ldssa.util;

import org.lds.ldsaccount.inject.LDSAccountModule;
import org.lds.ldssa.inject.CommonMockTestModule;
import org.lds.ldssa.inject.CommonTestModule;
import org.lds.ldssa.inject.DbTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CommonTestModule.class,
        CommonMockTestModule.class,
        LDSAccountModule.class,
        DbTestModule.class,
        CatalogUpdateUtilTest.CatalogUpdateUtilTestModule.class})
public interface CatalogUpdateUtilTestComponent {
    void inject(CatalogUpdateUtilTest target);

    void inject(UriUtilTest uriUtilTest);
}