package org.lds.ldssa.util.annotations

import org.apache.commons.lang3.StringUtils
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.model.database.catalog.item.ItemManager
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadata
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.highlight.Highlight
import org.lds.ldssa.model.database.userdata.link.Link
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.util.AnalyticsUtil
import org.lds.ldssa.util.CitationUtil
import org.lds.ldssa.util.ContentItemUtil
import org.lds.ldssa.util.HighlightColor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LinkUtil @Inject
constructor(private val linkManager: LinkManager,
            private val annotationManager: AnnotationManager,
            private val itemManager: ItemManager,
            private val subItemMetadataManager: SubItemMetadataManager,
            private val analyticsUtil: AnalyticsUtil,
            private val citationUtil: CitationUtil,
            private val contentItemUtil: ContentItemUtil)
{

    fun add(annotationId: Long, docId: String?, paragraphAid: String, name: String) {
        add(createLink(annotationId, docId, paragraphAid, name))
    }

    fun add(annotationId: Long, docId: String?, paragraphAids: Collection<String>, name: String) {
        add(createLink(annotationId, docId, paragraphAids, name))
    }

    fun add(link: Link) {
        annotationManager.beginTransaction()

        linkManager.save(link)

        annotationManager.updateLastModified(link.annotationId, true)

        annotationManager.endTransaction(true)

        logAnalytics(link.annotationId, Analytics.ChangeType.CREATE)
    }

    fun update(annotationId: Long, oldLinkId: Long, docId: String?, paragraphAids: Collection<String>, name: String) {
        val newLink = createLink(annotationId, docId, paragraphAids, name)
        update(oldLinkId, newLink)
    }

    fun update(oldLinkId: Long, newLink: Link) {
        annotationManager.beginTransaction()

        // delete the old link
        linkManager.delete(oldLinkId)

        // create the new link
        linkManager.save(newLink)

        annotationManager.updateLastModified(newLink.annotationId, true)

        annotationManager.endTransaction(true)

        logAnalytics(newLink.annotationId, Analytics.ChangeType.UPDATE)
    }

    fun delete(annotationId: Long, linkId: Long) {
        annotationManager.beginTransaction()

        if (linkManager.delete(linkId) > 0) {
            annotationManager.updateLastModified(annotationId, true)
            logAnalytics(annotationId, Analytics.ChangeType.DELETE)
        }

        annotationManager.endTransaction(true)
    }

    fun findInverseLinkAnnotation(annotation: Annotation, contentItemId: Long, subItemId: Long): Annotation? {
        val docId = subItemMetadataManager.findDocIdBySubItemId(contentItemId, subItemId)
        val links = annotation.links
        if (docId == null || links.isEmpty()) {
            return null
        }

        return links
                .firstOrNull { it.docId == docId }
                ?.let { createInverseLinkAnnotation(it) }
    }

    fun createInverseLinkAnnotation(link: Link): Annotation? {
        val annotation = annotationManager.findByRowId(link.annotationId)
        annotationManager.findFullAnnotationData(annotation)
        if (annotation == null || annotation.highlights.isEmpty()) {
            return null
        }

        var subItemMetadata: SubItemMetadata? = null
        if (annotation.docId != null) {
            subItemMetadata = subItemMetadataManager.findByDocId(annotation.docId!!)
        }
        if (subItemMetadata == null) {
            return null
        }

        // Retrieve the paragraph aids
        val paragraphAids = annotation.highlights.mapNotNull { it.paragraphAid }

        val inverseAnnotationId = Annotation.getInverseAnnotationId(annotation.id)
        val linkName: String
        val itemId = subItemMetadata.itemId
        linkName = if (contentItemUtil.isItemDownloadedAndOpen(itemId)) {
            citationUtil.createCitationText(itemId, subItemMetadata.subitemId, paragraphAids)
        } else {
            itemManager.findTitleById(itemId)
        }

        // Create the inverse (bi-directional) annotation
        val inverseAnnotation = Annotation()
        inverseAnnotation.id = inverseAnnotationId
        inverseAnnotation.uniqueId = Annotation.getInverseUniqueId(annotation.uniqueId)
        inverseAnnotation.lastModified = annotation.lastModified

        // Create the invisible highlight
        val inverseHighlight = Highlight()
        inverseHighlight.paragraphAid = link.getParagraphAids()[0]
        inverseHighlight.color = HighlightColor.CLEAR.name
        inverseAnnotation.highlights.add(inverseHighlight)

        // Create the link
        val inverseLink = createLink(inverseAnnotationId, annotation.docId, paragraphAids, linkName)
        inverseAnnotation.links.add(inverseLink)

        return inverseAnnotation
    }

    private fun createLink(annotationId: Long, docId: String?, paragraphAid: String, name: String): Link {
        val link = Link()
        link.annotationId = annotationId
        link.docId = docId
        link.paragraphAid = paragraphAid
        link.name = name
        link.contentVersion = subItemMetadataManager.findDocVersionByDocId(docId)
        return link
    }

    private fun createLink(annotationId: Long, docId: String?, paragraphAids: Collection<String>, name: String): Link {
        val link = Link()
        link.annotationId = annotationId
        link.docId = docId
        link.setParagraphAids(paragraphAids)
        link.name = name
        link.contentVersion = subItemMetadataManager.findDocVersionByDocId(docId)
        return link
    }

    private fun logAnalytics(annotationId: Long, changeType: Analytics.ChangeType) {
        analyticsUtil.logContentAnnotated(annotationId, Analytics.AnnotationType.LINK, changeType)
    }

    companion object {
        const val MAX_VISIBLE_NAME_CHARS = 20

        fun formatViewableName(link: Link): String {
            return StringUtils.abbreviate(link.name, MAX_VISIBLE_NAME_CHARS)
        }
    }
}
