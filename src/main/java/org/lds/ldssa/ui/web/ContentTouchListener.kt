package org.lds.ldssa.ui.web

import android.graphics.PointF
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.util.TextHandleUtil
import org.lds.mobile.util.LdsUiUtil
import javax.inject.Inject

class ContentTouchListener(private val contentWebView: ContentWebView) {

    @Inject
    lateinit  var uiUtil: LdsUiUtil
    @Inject
    lateinit var textHandleUtil: TextHandleUtil
    @Inject
    lateinit var contentJsInvoker: ContentJsInvoker
    
    
    private var isDraggingLeft = false
    private var isDraggingRight = false
    private var touchOffsetX = 0
    private var touchOffsetY = 0
    private var gestureDetector: GestureDetector

    var lastTouchPoint: PointF = PointF(0f, 0f)
        private set

    init {
        Injector.get().inject(this)

        // handle double-tap
        gestureDetector = GestureDetector(contentWebView.context, ContentWebViewGestureDetector())
    }

    fun onTouchEvent(motionEvent: MotionEvent): Boolean {
        lastTouchPoint = PointF(motionEvent.x, motionEvent.y)
        return gestureDetector.onTouchEvent(motionEvent) || actionTouch(motionEvent)
    }

    private fun actionTouch(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN && actionTouchDown(ev)) {
            return true
        }

        if (ev.action == MotionEvent.ACTION_MOVE && actionTouchMove(ev)) {
            return true
        }

        if (ev.action == MotionEvent.ACTION_UP && actionTouchUp()) {
            return true
        }

        return false
    }

    private fun actionTouchDown(motionEvent: MotionEvent): Boolean {
        val density = uiUtil.getDeviceDisplayDensity(contentWebView.context)

        contentJsInvoker.touchDown(contentWebView, uiUtil.createDensityPosition(contentWebView.context, motionEvent))
        if (contentWebView.isInSelectMode()) {
            isDraggingLeft = pointIsTouchingHandle(contentWebView.leftHandleImageView, motionEvent.x.toInt(), motionEvent.y.toInt())
            isDraggingRight = pointIsTouchingHandle(contentWebView.rightHandleImageView, motionEvent.x.toInt(), motionEvent.y.toInt())
            if (isDraggingRight) {
                isDraggingLeft = false
                val adjustX = contentWebView.leftHandleImageView.width.toFloat() / 2.0f
                val x = ((contentWebView.leftHandleImageView.left + adjustX) / density).toInt() + DRAG_ADJUST_X
                val y = (textHandleUtil.getHandleTop(contentWebView, contentWebView.leftHandleImageView) / density).toInt() - DRAG_ADJUST_Y
                actionTouchDownOnDragHandle(x, y, true)
                return true
            } else if (isDraggingLeft) {
                isDraggingRight = false
                val adjustX = contentWebView.rightHandleImageView.width.toFloat() / 2.0f
                val x = ((contentWebView.rightHandleImageView.left + adjustX) / density).toInt() - DRAG_ADJUST_X
                val y = (textHandleUtil.getHandleTop(contentWebView, contentWebView.rightHandleImageView) / density).toInt() - DRAG_ADJUST_Y
                actionTouchDownOnDragHandle(x, y, false)
                return true
            }
        }

        return false
    }

    private fun actionTouchDownOnDragHandle(x: Int, y: Int, right: Boolean) {
        val annotation = contentWebView.getSelectedAnnotation()
        if (annotation != null) {
            contentJsInvoker.enterSelectModeFromHandle(contentWebView, annotation, x, y, right, uiUtil.createDensityPosition(contentWebView.context, touchOffsetX, touchOffsetY))
        }
    }

    private fun actionTouchMove(motionEvent: MotionEvent): Boolean {
        if (contentWebView.isInSelectMode()) {
            var x = motionEvent.x.toInt()
            var y = motionEvent.y.toInt()

            contentJsInvoker.onTouchMove(contentWebView, uiUtil.createDensityPosition(contentWebView.context, motionEvent))

            y = y - touchOffsetY + contentWebView.scrollY - contentWebView.paddingTop
            if (isDraggingRight) {
                x = (x + touchOffsetX - contentWebView.rightHandleImageView.width / 2.0f).toInt()
                textHandleUtil.updateHandle(contentWebView.rightHandleImageView, x, y, View.VISIBLE)
                contentWebView.hideHighlightPopup()
                return true
            } else if (isDraggingLeft) {
                x = (x + touchOffsetX - contentWebView.leftHandleImageView.width / 2.0f).toInt()
                textHandleUtil.updateHandle(contentWebView.leftHandleImageView, x, y, View.VISIBLE)
                contentWebView.hideHighlightPopup()
                return true
            }
        }

        return false
    }

    private fun actionTouchUp(): Boolean {
        contentJsInvoker.onTouchUp(contentWebView)
        if (contentWebView.isInSelectMode() && (isDraggingLeft || isDraggingRight)) {
            clearDraggingHandleFlags()
            return true
        }

        return false
    }

    fun clearDraggingHandleFlags() {
        isDraggingLeft = false
        isDraggingRight = false
    }

    private inner class ContentWebViewGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onDoubleTap(e: MotionEvent): Boolean {
            contentWebView.onDoubleTap()
            return true
        }
    }

    private fun pointIsTouchingHandle(handle: View, x: Int, yIn: Int): Boolean {
        var y = yIn
        y += contentWebView.scrollY
        var touchingHandle = handle.visibility == View.VISIBLE
        touchingHandle = touchingHandle && y >= handle.top && y <= handle.bottom
        touchingHandle = touchingHandle && x >= handle.left && x <= handle.right
        if (touchingHandle) {
            val density = uiUtil.getDeviceDisplayDensity(contentWebView.context)
            touchOffsetX = (handle.left - x + handle.width / 2.0f).toInt()
            touchOffsetY = (y - handle.top + TOUCH_ADJUST_Y * density).toInt()
            return true
        }
        return false
    }

    companion object {
        private val TOUCH_ADJUST_Y = 18.0f
        private val DRAG_ADJUST_X = 3
        private val DRAG_ADJUST_Y = 5
    }
}