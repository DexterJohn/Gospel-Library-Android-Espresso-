package org.lds.ldssa.util

import android.app.Application
import android.support.annotation.StringRes
import android.widget.Toast
import kotlinx.coroutines.experimental.launch
import org.lds.mobile.coroutine.CoroutineContextProvider
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ToastUtil @Inject
constructor(private val application: Application?, private val cc: CoroutineContextProvider) {

    fun show(@StringRes stringRes: Int, duration: Int) {
        if (application == null) {    // application will be null in unit tests
            Timber.d("String res $stringRes")
        } else {
            launch(cc.ui) { Toast.makeText(application, stringRes, duration).show() }
        }
    }

    fun show(text: String, duration: Int) {
        if (application == null) {    // application will be null in unit tests
            Timber.d(text)
        } else {
            launch(cc.ui) { Toast.makeText(application, text, duration).show() }
        }
    }

    fun showLong(@StringRes stringRes: Int) {
        show(stringRes, Toast.LENGTH_LONG)
    }

    fun showLong(@StringRes stringRes: Int, vararg formatArgs: Any) {
        if (application == null) {    // application will be null in unit tests
            Timber.d("String res $stringRes: $formatArgs")
        } else {
            show(application.getString(stringRes, *formatArgs), Toast.LENGTH_LONG)
        }
    }

    fun showLong(string: String) {
        show(string, Toast.LENGTH_LONG)
    }

    fun showShort(@StringRes stringRes: Int) {
        show(stringRes, Toast.LENGTH_SHORT)
    }

    fun showShort(string: String) {
        show(string, Toast.LENGTH_SHORT)
    }

    fun showShort(@StringRes stringRes: Int, vararg formatArgs: Any) {
        if (application == null) {    // application will be null in unit tests
            Timber.d("String res $stringRes: $formatArgs")
        } else {
            show(application.getString(stringRes, *formatArgs), Toast.LENGTH_SHORT)
        }
    }
}
