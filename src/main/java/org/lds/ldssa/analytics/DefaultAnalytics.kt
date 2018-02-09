package org.lds.ldssa.analytics

import android.app.Application

import org.lds.ldssa.BuildConfig
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.prefs.Prefs
import org.lds.mobile.analytics.LDSAnalytics
import org.lds.mobile.metadata.MetaData
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime

import java.util.ArrayList
import javax.inject.Inject

class DefaultAnalytics(application: Application) : Analytics {
    private var lastDimensionUpdate = LocalDateTime.MIN
    private var dimensions = mutableListOf<String>()
    private val ldsAnalytics: LDSAnalytics

    @Inject
    lateinit var prefs: Prefs

    init {
        Injector.get().inject(this)
        ldsAnalytics = LDSAnalytics()
        if (prefs.isAnalyticsEnabled) {
            val metaData = MetaData(application)
            ldsAnalytics.configure(application, metaData.getString("LOCALYTICS_APP_KEY"), null)
            ldsAnalytics.setLogLevel(if (BuildConfig.DEBUG) LDSAnalytics.LogLevel.VERBOSE else LDSAnalytics.LogLevel.UPLOAD)
        } else {
            ldsAnalytics.configure(null, null, null)
        }
    }

    override fun upload() {
        ldsAnalytics.upload(true)
    }

    override fun setDimensions(dimensions: List<String>) {
        ldsAnalytics.setDimensions(dimensions)
    }

    override fun postEvent(eventId: String, attributes: Map<String, String>) {
        updateDimensions()
        ldsAnalytics.logEvent(eventId, attributes)
    }

    override fun postEvent(eventId: String) {
        updateDimensions()
        ldsAnalytics.logEvent(eventId)
    }

    override fun postScreen(screen: String) {
        ldsAnalytics.logScreen(screen)
    }

    private fun updateDimensions() {
        if (dimensions.isEmpty() || LocalDateTime.now().isAfter(lastDimensionUpdate.plusHours(1))) {
            dimensions.clear()

            val isSunday = LocalDateTime.now().dayOfWeek == DayOfWeek.SUNDAY
            dimensions.add(if (isSunday) Analytics.Dimension.WEEK_DAY_SUNDAY else Analytics.Dimension.WEEK_DAY_MON_SAT)

            lastDimensionUpdate = LocalDateTime.now()
            setDimensions(dimensions)
        }
    }
}
