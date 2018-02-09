package org.lds.ldssa.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class AppJobCreator @Inject
constructor(private val annotationSyncJobProvider: Provider<AnnotationSyncJob>) : JobCreator {

    override fun create(tag: String): Job? {
        when (tag) {
            AnnotationSyncJob.TAG -> return annotationSyncJobProvider.get()
            else -> {
                Timber.e("CANNOT find job for tag [%s].  Be sure add the creation to AppJobCreator.", tag)
                return null
            }
        }
    }
}
