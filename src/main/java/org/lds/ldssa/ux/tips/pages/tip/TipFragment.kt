package org.lds.ldssa.ux.tips.pages.tip

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.devbrackets.android.exomedia.listener.OnCompletionListener
import com.devbrackets.android.exomedia.listener.OnPreparedListener
import kotlinx.android.synthetic.main.fragment_tip.*
import me.eugeniomarletti.extras.bundle.BundleExtra
import me.eugeniomarletti.extras.bundle.base.Long
import me.eugeniomarletti.extras.bundle.base.String
import org.lds.ldssa.R
import org.lds.ldssa.glide.GlideApp
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.tips.tip.Tip
import org.lds.ldssa.model.prefs.Prefs
import org.lds.ldssa.ui.fragment.BaseFragment
import org.lds.ldssa.util.GLFileUtil
import org.lds.mobile.extras.SelfFragmentCompanion
import org.lds.mobile.glide.ImageRenditions
import java.io.File
import javax.inject.Inject

class TipFragment : BaseFragment(), OnPreparedListener, OnCompletionListener {

    @Inject
    lateinit var fileUtil: GLFileUtil
    @Inject
    lateinit var prefs: Prefs
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TipViewModel::class.java) }

    private var isVideoPrepared = false

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.isTablet = resources.getBoolean(R.bool.tablet)

        setupViewModelObservers()

        arguments?.let {
            viewModel.setTipId(it.tipId)
        }
    }

    fun setupViewModelObservers() {
        viewModel.tip.observe { tip ->
            tip ?: return@observe

            if (viewModel.hasVideo()) {
                tipVideoView.post(adjustSizes)
                tipVideoView.setOnClickListener { restartVideo() }
                previewImageView.setOnClickListener { swapToVideoAndPlay() }
            } else {
                showPreviewImage(tip)
            }

            val args = arguments ?: error("Args required")
            tipToolbar.title = args.toolbarTitle

            titleTextView.text = tip.title
            descriptionTextView.text = tip.description
        }
    }

    override fun getLayoutResourceId() = R.layout.fragment_tip

    override fun onPause() {
        super.onPause()
        checkVideoView(false)
    }

    override fun onResume() {
        super.onResume()
        // if the user is actually viewing this fragment (in view pager)
        if (userVisibleHint) {
            viewModel.saveTipViewed()
            checkVideoView(userVisibleHint)
        }
    }

    override fun setUserVisibleHint(visible: Boolean) {
        super.setUserVisibleHint(visible)
        if (visible && isResumed) {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (viewModel.hasVideo() && tipVideoView != null) {
            viewModel.currentPosition = tipVideoView.currentPosition
        }
    }

    override fun onPrepared() {
        isVideoPrepared = true
        swapToVideoAndPlay()
    }

    private fun swapToVideoAndPlay() {
        if (viewModel.hasVideo() && tipVideoView != null && isVideoPrepared) {
            previewImageView.visibility = View.GONE
            if (viewModel.currentPosition > 0) {
                tipVideoView.seekTo(viewModel.currentPosition)
            }
            tipVideoView.start()
        }
    }

    override fun onCompletion() {
        restartVideo()
    }

    private fun restartVideo() {
        if (tipVideoView.isPlaying) {
            tipVideoView.seekTo(0)
            tipVideoView.start()
        } else {
            // following method can only be called when video is FINISHED playing
            tipVideoView.restart()
        }
    }

    private fun setupVideoView() {
        if (prefs.isMobileNetworkLimited) {
            return
        }

        if (viewModel.hasVideo().not()) {
            return
        }

        if (tipVideoView.videoUri == null) {
            tipVideoView.setOnPreparedListener(this)
            tipVideoView.setOnCompletionListener(this)

            val uri = viewModel.tip.value?.getPlaybackUrl(viewModel.isTablet)
            if (uri != null) {
                tipVideoView.setVideoURI(Uri.parse(uri))
            }
        } else if (!tipVideoView.isPlaying && isVideoPrepared) {
            tipVideoView.start()
        }
    }

    private fun checkVideoView(visible: Boolean) {
        if (viewModel.hasVideo() && tipVideoView != null) {
            if (visible) {
                setupVideoView()
            } else if (tipVideoView.isPlaying) {
                tipVideoView.pause()
            }
        }
    }

    private fun showPreviewImage(tip: Tip) {
        val previewImageFile = getPreviewImageFile(tip)
        if (previewImageFile != null) {
            GlideApp.with(activity)
                    .load(previewImageFile)
                    .centerCrop()
                    .into(previewImageView)
        } else {
            val renditions = tip.getImageRenditions(viewModel.isTablet)
            if (renditions.isNotBlank()) {
                GlideApp.with(activity)
                        .load(ImageRenditions(renditions))
                        .centerCrop()
                        .into(previewImageView)
            }
        }
    }

    private fun getPreviewImageFile(tip: Tip): File? {
        val filename = tip.getImageFilename(viewModel.isTablet)
        return if (filename.isNullOrBlank().not()) {
            File(fileUtil.tipsDir, filename)
        } else {
            null
        }

    }

    // We're going to use the height of the preview image
    // to determine the size of the video.
    private val adjustSizes = Runnable {
        val tip = viewModel.tip.value
        tip ?: return@Runnable

        val previewImageFile = getPreviewImageFile(tip)

        if (previewImageFile != null) {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(previewImageFile.absolutePath, options)

            val params = tipVideoView.layoutParams
            params.height = (options.outHeight / (options.outWidth.toFloat() / tipVideoView.width.toFloat())).toInt()
            tipVideoView.layoutParams = params

            showPreviewImage(tip)
        }
    }

    companion object : SelfFragmentCompanion<Companion>(TipFragment::class) {
        private var Bundle.tipId by BundleExtra.Long(0L)
        private var Bundle.toolbarTitle by BundleExtra.String("")

        fun newInstance(context: Context, screenId: Long, tipId: Long, toolbarTitle: String): TipFragment {
            return instantiate(context) {
                BaseFragment.getBaseBundle(it, screenId)
                it.tipId = tipId
                it.toolbarTitle = toolbarTitle
            }
        }
    }
}
