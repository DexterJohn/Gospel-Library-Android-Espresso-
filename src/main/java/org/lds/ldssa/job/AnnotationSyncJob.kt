package org.lds.ldssa.job

import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest

import org.lds.ldssa.sync.AnnotationSync

import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AnnotationSyncJob @Inject
constructor(private val annotationSync: AnnotationSync) : Job() {

    override fun onRunJob(params: Job.Params): Job.Result {
        annotationSync.sync()
        return Job.Result.SUCCESS
    }

    companion object {
        const val TAG = "AnnotationSyncJob"

        private val EXECUTION_WINDOW_START_NOW = TimeUnit.SECONDS.toMillis(1)
        private val EXECUTION_WINDOW_END_NOW = TimeUnit.SECONDS.toMillis(2)
        private val EXECUTION_WINDOW_START = TimeUnit.SECONDS.toMillis(10)
        private val EXECUTION_WINDOW_END = TimeUnit.SECONDS.toMillis(30)

        fun schedule(now: Boolean = false) {
            val startExec = if (now) EXECUTION_WINDOW_START_NOW else EXECUTION_WINDOW_START
            val endExec = if (now) EXECUTION_WINDOW_END_NOW else EXECUTION_WINDOW_END

            JobRequest.Builder(AnnotationSyncJob.TAG)
                    .setRequirementsEnforced(true)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setExecutionWindow(startExec, endExec)
                    .setUpdateCurrent(true)
                    .build()
                    .schedule()
        }

        fun cancelAll() {
            JobManager.instance().cancelAllForTag(TAG)
        }
    }
}
