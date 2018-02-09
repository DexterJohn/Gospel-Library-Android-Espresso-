package org.lds.ldssa.model.webservice.annotation.dto.annotation


import org.threeten.bp.LocalDateTime

/**
 * This should ONLY be used in Unit tests (this is not optimized for small devices... parse on-the-fly instead)
 */
@Deprecated("Marked deprecated so that the developer will not use this in production code by accident")
class DtoSyncAnnotations {
    var count: Long? = null
    var since: LocalDateTime? = null
    var clientTime: LocalDateTime? = null
    var changes: List<DtoAnnotationChange>? = null
}
