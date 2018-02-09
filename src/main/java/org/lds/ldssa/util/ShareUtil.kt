package org.lds.ldssa.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.text.Html
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import me.eugeniomarletti.extras.intent.base.Serializable
import me.eugeniomarletti.extras.intent.base.String
import org.apache.commons.lang3.StringEscapeUtils
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.model.database.catalog.subitemmetadata.SubItemMetadataManager
import org.lds.ldssa.model.database.content.subitem.SubItemManager
import org.lds.ldssa.model.database.userdata.annotation.Annotation
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ux.content.item.ContentRequestCode
import org.lds.ldssa.ux.share.ShareIntentActivity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShareUtil @Inject
constructor(private val subItemManager: SubItemManager,
            private val subItemMetadataManager: SubItemMetadataManager,
            private val analytics: Analytics,
            private val analyticsUtil: AnalyticsUtil,
            private val uriUtil: UriUtil,
            private val annotationUiUtil: AnnotationUiUtil,
            private val contentItemUtil: ContentItemUtil) {

    fun onShowShareDialogForVideo(activity: Activity, screenId: Long, contentItemId: Long, subItemId: Long, uri: String, title: String, content: String?) {
        val intent = Intent(activity, ShareIntentActivity::class.java)
        with(ShareIntentActivity.IntentOptions) {
            intent.mimeType = ShareUtil.MimeType.TEXT_PLAIN
        }
        with(IntentOptions) {
            intent.scopeType = ShareUtil.ScopeType.DOCUMENT
            intent.contentItemId = contentItemId
            intent.subItemId = subItemId
            intent.shareUri = uri
            intent.shareTitle = title
            intent.shareText = content ?: ""
        }
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        activity.startActivityForResult(intent, ContentRequestCode.REQUEST_APPLICATION_SHARE.ordinal)
    }

    fun onShowShareDialogForContentSnippet(activity: Activity?, screenId: Long, contentItemId: Long, subItemId: Long, uri: String, title: String?, content: String?) {
        activity ?: return
        val intent = Intent(activity, ShareIntentActivity::class.java)
        with(ShareIntentActivity.IntentOptions) {
            intent.mimeType = ShareUtil.MimeType.TEXT_PLAIN
        }
        with(IntentOptions) {
            intent.scopeType = ShareUtil.ScopeType.SNIPPET
            intent.contentItemId = contentItemId
            intent.subItemId = subItemId
            intent.shareUri = uri
            intent.shareTitle = title ?: ""
            intent.shareText = content ?: ""
        }
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        activity.startActivityForResult(intent, ContentRequestCode.REQUEST_APPLICATION_SHARE.ordinal)
    }

    fun showShareDialogForContent(activity: Activity?, screenId: Long, contentItemId: Long, subItemId: Long) {
        activity ?: return

        val intent = Intent(activity, ShareIntentActivity::class.java)
        with(ShareIntentActivity.IntentOptions) {
            intent.mimeType = ShareUtil.MimeType.TEXT_PLAIN
        }
        with(IntentOptions) {
            intent.scopeType = ShareUtil.ScopeType.DOCUMENT
            intent.contentItemId = contentItemId
            intent.subItemId = subItemId
        }
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        activity.startActivityForResult(intent, ContentRequestCode.REQUEST_APPLICATION_SHARE.ordinal)
    }

    fun showShareDialogForAnnotation(activity: Activity, screenId: Long, annotation: Annotation) {
        val subItemMetadata = annotation.docId?.let {
            subItemMetadataManager.findByDocId(it)
        }

        val intent = Intent(activity, ShareIntentActivity::class.java)
        with(ShareIntentActivity.IntentOptions) {
            intent.mimeType = ShareUtil.MimeType.TEXT_PLAIN
        }
        with(IntentOptions) {
            intent.scopeType = ShareUtil.ScopeType.SNIPPET
            if (subItemMetadata != null && contentItemUtil.isItemDownloadedAndOpen(subItemMetadata.itemId)) {
                intent.contentItemId = subItemMetadata.itemId
                intent.subItemId = subItemMetadata.subitemId
            }
            intent.shareUri = uriUtil.getSharableUri(annotation)
            intent.shareTitle = annotationUiUtil.getSharableTitle(annotation)
            intent.shareText = annotationUiUtil.getSharableText(annotation)
        }
        intent.putExtra(BaseActivity.EXTRA_SCREEN_ID, screenId)
        activity.startActivityForResult(intent, ContentRequestCode.REQUEST_APPLICATION_SHARE.ordinal)
    }

    fun processShareRequest(activity: Activity, intent: Intent) {
        with(ShareIntentActivity.IntentOptions) {
            val info = intent.shareResultInfo ?: return
            with(IntentOptions) {
                val contentItemId = intent.contentItemId
                addContentToIntent(intent, contentItemId, intent.subItemId)

                //Determines the share destination
                val packageName = info.activityInfo.packageName.toLowerCase()
                val scopeType = intent.scopeType ?: error("scope is required")
                when {
                    packageName.contains(PACKAGE_FACEBOOK) -> showFacebookShare(activity, intent.shareTitle, intent.shareText, intent.shareUri, null)
                    packageName.contains(PACKAGE_TWITTER) -> showShare(activity, info, null, intent.shareText, intent.shareUri)
                    else -> showShare(activity, info, intent.shareTitle, intent.shareText, intent.shareUri)
                }

                logAnalytics(activity, info, contentItemId, intent.shareUri, scopeType)
            }
        }
    }

    private fun logAnalytics(activity: Activity, info: ResolveInfo, contentItemId: Long, shareUrl: String, scopeType: ScopeType) {
        with(Analytics.Attribute) {
            val attributes = hashMapOf<String, String>(
                    CONTENT_GROUP to analyticsUtil.findContentGroupByContentItemId(contentItemId),
                    CONTENT_SCOPE to scopeType.type,
                    CONTENT_LANGUAGE to analyticsUtil.findContentLanguageByContentItemId(contentItemId),
                    URI to shareUrl,
                    SHARE_TYPE to info.activityInfo.applicationInfo.loadLabel(activity.packageManager).toString()
            )
            analytics.postEvent(Analytics.Event.CONTENT_SHARED, attributes)
        }
    }

    /**
     * Creates and displays the Facebook share dialog
     *
     * @param activity The activity to show the ShareDialog with
     * @param title The title for the content to share
     * @param content The description for the post
     * @param url The String representation for the url to share
     * @param imageUrl The url for the image to share
     */
    private fun showFacebookShare(activity: Activity, title: String, content: String, url: String?, imageUrl: String?) {
        val builder = ShareLinkContent.Builder().apply {
            setContentTitle(title)
            setContentDescription(Html.fromHtml(content).toString())

            if (!url.isNullOrBlank()) {
                setContentUrl(Uri.parse(url))
            }

            if (!imageUrl.isNullOrBlank()) {
                setImageUrl(Uri.parse(imageUrl))
            }
        }
        ShareDialog.show(activity, builder.build())
    }

    private fun addContentToIntent(intent: Intent, contentItemId: Long, subItemId: Long) {
        if (contentItemId <= 0 || subItemId <= 0) {
            return
        }

        with(IntentOptions) {
            if (intent.shareTitle.isBlank()) {
                intent.shareTitle = subItemManager.findTitleById(contentItemId, subItemId)
            }

            if (intent.shareUri.isBlank()) {
                intent.shareUri = subItemManager.getWebUrl(contentItemId, subItemId)
            }
        }
    }

    private fun showShare(context: Context, info: ResolveInfo, title: String?, content: CharSequence?, url: String?) {
        val shareIntent = Intent(Intent.ACTION_SEND)

        if (supportsSendHtml(context, info.activityInfo.packageName)) {
            addContentHtmlToIntent(shareIntent, title, content, url)
            shareIntent.type = MimeType.TEXT_HTML.type
        } else {
            addContentPlainTextToIntent(shareIntent, title, content, url)
            shareIntent.type = MimeType.TEXT_PLAIN.type
        }

        shareIntent.setClassName(info.activityInfo.packageName, info.activityInfo.name)
        shareIntent.putExtra(Intent.EXTRA_TITLE, title)

        context.startActivity(shareIntent)
    }

    private fun addContentPlainTextToIntent(intent: Intent, title: String?, content: CharSequence?, url: String?) {
        val shareText = StringBuilder()

        // content
        content?.let {
            //If we don't support html then strip any HTML tags
            shareText.append(Html.fromHtml(it.toString()).toString().trim())
        }

        // title
        if (!title.isNullOrBlank()) {
            if (shareText.isNotBlank()) shareText.append("\n\n")
            shareText.append(title)
        }

        // url
        if (!url.isNullOrBlank()) {
            if (shareText.isNotBlank()) shareText.append("\n\n")

            shareText.append(url)
        }

        intent.putExtra(Intent.EXTRA_TEXT, shareText.toString())
    }

    private fun addContentHtmlToIntent(intent: Intent, title: String?, content: CharSequence?, url: String?) {
        val shareText = StringBuilder()

        if (content.isNullOrBlank().not()) {
            var formattedContent = content.toString().trim().replace("\n", "<br/>")

            while(formattedContent.endsWith("<br/>")) {
                formattedContent = formattedContent.removeSuffix("<br/>")
            }
            shareText.append(formattedContent)
        }

        if (!title.isNullOrBlank()) {
            if (shareText.isNotBlank()) shareText.append("<br/><br/>")
            shareText.append(title)
        }

        // url
        if (!url.isNullOrBlank()) {
            if (shareText.isNotBlank()) shareText.append("<br/><br/>")
            shareText.append(StringEscapeUtils.escapeHtml4(url)) // some urls (select a scripture) create urls that need to be escaped
        }

        val intentShareText = shareText.toString()
        intent.putExtra(Intent.EXTRA_HTML_TEXT, intentShareText)
        intent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(intentShareText).toString())
    }

    /**
     * Determines if the selected `packageName` supports html content.
     * TESTING NOTE: as of Oct 2017 Google Messenger supports Html, Google Hangouts does not
     *
     * @param context The context to use when determining html support
     * @param packageName The package name to determine if it supports html
     * @return True if the packageName supports html with the `shareIntent`
     */
    private fun supportsSendHtml(context: Context, packageName: String): Boolean {
        if (packageName == PACKAGE_WHATSAPP) {
            // WhatsApp says it supports html content, but it seems to prevent sharing if we pass html content
            return false
        }

        val supportCheckIntent = Intent(Intent.ACTION_SEND).apply {
            type = MimeType.TEXT_HTML.type
        }

        val htmlSupportedActivities = context.packageManager.queryIntentActivities(supportCheckIntent, 0)

        return htmlSupportedActivities.any { it.activityInfo.packageName == packageName }
    }

    enum class MimeType(val type: String) {
        TEXT_PLAIN("text/plain"),
        TEXT_HTML("text/html")
    }

    enum class ScopeType(val type: String) {
        DOCUMENT("Document"),
        SNIPPET("Snippet")
    }

    object IntentOptions {
        var Intent.shareTitle by IntentExtra.String("")
        var Intent.shareText by IntentExtra.String("")
        var Intent.shareUri by IntentExtra.String("")
        var Intent.contentItemId by IntentExtra.Long(0L)
        var Intent.subItemId by IntentExtra.Long(0L)
        var Intent.scopeType by IntentExtra.Serializable<ScopeType>()
    }

    companion object {
        const val PACKAGE_FACEBOOK = "facebook.katana"
        const val PACKAGE_GOOGLE_PLUS = "google.android.apps.plus"
        const val PACKAGE_TWITTER = "twitter.android"
        const val PACKAGE_WHATSAPP = "com.whatsapp"
    }
}
