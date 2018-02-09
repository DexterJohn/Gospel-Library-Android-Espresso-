/*
 * Tip.kt
 *
 * Created: 02/09/2017 11:39:55
 */



package org.lds.ldssa.model.database.tips.tip

import org.apache.commons.lang3.StringUtils


class Tip : TipBaseRecord() {
    fun getPlaybackUrl(isTablet: Boolean) = if (isTablet) tabletVideoUrl else phoneVideoUrl
    fun hasVideo(isTablet: Boolean) = StringUtils.isNotBlank(getPlaybackUrl(isTablet))
    fun getImageFilename(isTablet: Boolean) = if (isTablet) tabletImageFilename else phoneImageFilename
    fun getImageRenditions(isTablet: Boolean) = if (isTablet) tabletImageRenditions else phoneImageRenditions
}