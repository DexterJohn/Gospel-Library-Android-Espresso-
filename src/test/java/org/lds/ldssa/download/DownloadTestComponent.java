package org.lds.ldssa.download;

import org.lds.ldsaccount.inject.LDSAccountModule;
import org.lds.ldssa.inject.CommonMockTestModule;
import org.lds.ldssa.inject.CommonTestModule;
import org.lds.ldssa.inject.DbSampleCatalogTestModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
        CommonTestModule.class,
        CommonMockTestModule.class,
        LDSAccountModule.class,
        DbSampleCatalogTestModule.class,
        DownloadContentTest.DownloadTestModule.class})
public interface DownloadTestComponent {
    void inject(DownloadContentTest target);
}