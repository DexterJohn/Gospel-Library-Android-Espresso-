package org.lds.ldssa.util.annotations

import org.lds.ldssa.model.database.userdata.annotation.Annotation
import javax.inject.Inject

class AnnotationListUtil
@Inject constructor() {

    fun annotationExistsInList(annotations: List<Annotation>?, uniqueId: String?): Boolean {
        return findAnnotationByUniqueId(annotations, uniqueId) != null
    }

    fun findAnnotationByUniqueId(annotations: List<Annotation>?, uniqueId: String?): Annotation? {
        if (annotations == null || uniqueId == null) {
            return null
        }

        return annotations.firstOrNull { it.uniqueId == uniqueId }
    }

    fun removeAnnotationFromListByUniqueId(annotations: MutableList<Annotation>?, uniqueId: String?): Boolean {
        if (annotations == null || uniqueId == null) {
            return false
        }

        val itr = annotations.iterator()
        while (itr.hasNext()) {
            val annotation = itr.next()
            if (annotation.uniqueId == uniqueId) {
                itr.remove()
                return true
            }
        }

        return false
    }

    fun replaceAnnotation(annotations: MutableList<Annotation>?, updatedAnnotation: Annotation?): Boolean {
        if (annotations == null || updatedAnnotation == null) {
            return false
        }

        for ((position, annotation) in annotations.withIndex()) {
            if (annotation.uniqueId == updatedAnnotation.uniqueId) {
                annotations[position] = updatedAnnotation
                return true
            }
        }

        return false
    }
}
