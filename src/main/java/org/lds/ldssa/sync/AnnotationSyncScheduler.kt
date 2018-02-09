package org.lds.ldssa.sync

import org.lds.ldssa.job.AnnotationSyncJob

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AnnotationSyncScheduler @Inject
constructor() {

    /**
     * Central location to initiate a background sync.  Also used in test to verify that sync has been called
     */
    fun scheduleSync() {
        AnnotationSyncJob.schedule()
    }
}
