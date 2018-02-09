package org.lds.ldssa.download

class InstallProgress @JvmOverloads constructor(val status: InstallStatus, val downloadProgress: Int, val failureReasonId: Int = NO_FAILURE) {
    companion object {
        const val INDETERMINATE_PROGRESS = -1
        const val NO_FAILURE = -1
    }
}
