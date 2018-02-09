package org.lds.ldssa.model.webservice.annotation.dto.folder


/**
 * This should ONLY be used in Unit tests (this is not optimized for small devices... parse on-the-fly instead)
 */
@Suppress("DEPRECATION")
@Deprecated("Marked deprecated so that the developer will not use this in production code by accident")
class DtoFolderSync {
    var syncFolders: DtoSyncFolders? = null
}
