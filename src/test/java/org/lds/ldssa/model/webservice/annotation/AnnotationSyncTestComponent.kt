package org.lds.ldssa.model.webservice.annotation

import dagger.Component
import org.lds.ldsaccount.inject.LDSAccountModule
import org.lds.ldssa.inject.CommonMockTestModule
import org.lds.ldssa.inject.CommonTestModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(CommonTestModule::class, CommonMockTestModule::class, LDSAccountModule::class, AnnotationSyncTestModule::class))
interface AnnotationSyncTestComponent {
    fun inject(test: AnnotationSyncTest)
}
