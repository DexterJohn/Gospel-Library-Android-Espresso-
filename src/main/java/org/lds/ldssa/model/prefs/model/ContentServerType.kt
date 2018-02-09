package org.lds.ldssa.model.prefs.model

import org.lds.ldssa.BuildConfig

enum class ContentServerType constructor(val baseUrl: String, val contentUrl: String) {
    PROD("http://edge.ldscdn.org", BuildConfig.CONTENT_PROD_LOCATION),
    BETA("http://edge.ldscdn.org", BuildConfig.CONTENT_BETA_LOCATION),
    STAGING("https://ldscdn.org/mobile/GospelStudy/n8kxfbdzzr82enr0qhh7pzbc777rx4uiuxxbkpvy/", BuildConfig.CONTENT_STAGING_LOCATION),
    TEMPLATE("https://ldscdn.org/mobile/GospelStudy/AwboX9kGSvtKZoT1NVdnYXFHbIXAGXmK9k5wll9a1dL9juuk57Uq/", BuildConfig.CONTENT_TEMPLATE_LOCATION),
    DEV("https://tech.lds.org/mobile/gospelstudy/staging-content/", BuildConfig.CONTENT_DEV_LOCATION),
}