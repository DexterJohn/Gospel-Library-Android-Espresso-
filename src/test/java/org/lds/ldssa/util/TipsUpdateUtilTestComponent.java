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
        TipsUpdateUtilTest.TipsUpdateUtilTestModule.class})
public interface TipsUpdateUtilTestComponent {
    void inject(TipsUpdateUtilTest target);
}