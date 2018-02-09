package org.lds.ldssa.download

enum class InstallStatus {
    DOWNLOAD_PAUSED,
    DOWNLOAD_PENDING,
    DOWNLOAD_RUNNING,
    DOWNLOAD_FAILED,
    DOWNLOAD_DOES_NOT_EXIST, // LDS Status for a download that does not exist... probably cancelled
    INSTALL_RUNNING,
    INSTALL_SUCCESSFUL,
    INSTALL_FAILED
}
