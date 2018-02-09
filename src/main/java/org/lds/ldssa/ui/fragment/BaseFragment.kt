package org.lds.ldssa.ui.fragment

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.experimental.Job
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.mobile.coroutine.CoroutineContextProvider
import org.lds.mobile.ui.fragment.LiveDataObserverFragment
import pocketbus.Bus
import pocketknife.BindArgument
import pocketknife.PocketKnife
import javax.inject.Inject

abstract class BaseFragment : LiveDataObserverFragment() {

    @Inject
    lateinit var bus: Bus
    @Inject
    lateinit var cc: CoroutineContextProvider

    @BindArgument(ARG_SCREEN_ID)
    var screenId: Long = 0

    private var compositeDisposable = CompositeDisposable()
    private var compositeJob: Job? = null
    protected var currentSnackbar: Snackbar? = null
    private var unBinder: Unbinder? = null

    @LayoutRes
    protected abstract fun getLayoutResourceId(): Int

    protected open fun hasBusSubscriptions(): Boolean {
        return false
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        PocketKnife.restoreInstanceState(this, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.get().inject(this)

        PocketKnife.bindArguments(this)
        PocketKnife.bindExtras(this, activity?.intent)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(getLayoutResourceId(), container, false)
        unBinder = ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onPostViewCreated()
    }

    override fun onStart() {
        super.onStart()
        if (hasBusSubscriptions()) {
            bus.register(this)
        }
    }

    override fun onStop() {
        compositeDisposable.dispose()
        compositeJob?.cancel()
        if (hasBusSubscriptions()) {
            bus.unregister(this)
        }
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        unBinder?.unbind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        PocketKnife.saveInstanceState(this, outState)
    }

    fun addDisposable(disposable: Disposable) {
        if (compositeDisposable.isDisposed) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable)
    }

    fun addJob(job: Job) {
        if (compositeJob == null) {
            compositeJob = Job()
        }
        compositeJob?.attachChild(job)
    }

    protected open fun onPostViewCreated() {
        // nothing here but sub classes can override this
    }

    protected fun showUndoSnackbar(message: CharSequence, undoRunnable: Runnable, commitRunnable: Runnable) {
        val parentView = view ?: activity?.window?.decorView
        parentView ?: return

        currentSnackbar = Snackbar.make(parentView, message, Snackbar.LENGTH_LONG)
                .setAction(R.string.undo, { undoRunnable.run() })
                .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(snackbar: Snackbar?, @DismissEvent event: Int) {
                        if (event != Snackbar.Callback.DISMISS_EVENT_ACTION) {
                            commitRunnable.run()
                        }

                        currentSnackbar = null
                    }
                })

        currentSnackbar?.show()
    }

    companion object {
        const val ARG_SCREEN_ID = "ARG_SCREEN_ID"

        @JvmStatic
        fun getBaseBundle(args: Bundle, screenId: Long) {
            args.putLong(ARG_SCREEN_ID, screenId)
        }

        @JvmStatic
        fun getBaseBundle(screenId: Long) = Bundle().apply {
            putLong(ARG_SCREEN_ID, screenId)
        }
    }
}
