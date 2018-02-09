package org.lds.ldssa.glide

import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class AppGlideModule : AppGlideModule() {
    // for performance reasons, don't support pre-Glide4... (no need to scan manifest for old glide modules)
    override fun isManifestParsingEnabled(): Boolean {
        return false
    }
}