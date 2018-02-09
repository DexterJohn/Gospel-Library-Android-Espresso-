package org.lds.ldssa.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.View;

import org.apache.commons.io.IOUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.types.HighlightAnnotationStyle;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.ui.util.LdsDrawableUtil;

import java.io.File;
import java.io.FileOutputStream;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class ImageUtil {
    private static final int SCREEN_SHOT_QUALITY = 90;
    private static final float THUMB_SCALE_PERCENT = 0.7f;
    private static final float[] NEGATIVE = {
            -1.0f,      0,       0,       0,   255, // red
            0,  -1.0f,       0,       0,   255, // green
            0,      0,   -1.0f,       0,   255, // blue
            0,      0,       0,    1.0f,     0  // alpha (identity)
    };
    private static final ColorMatrixColorFilter NEGATIVE_COLOR_MATRIX;

    private final Prefs prefs;
    private final GLFileUtil fileUtil;

    static {
        NEGATIVE_COLOR_MATRIX = new ColorMatrixColorFilter(NEGATIVE);
    }

    @Inject
    public ImageUtil(Prefs prefs, GLFileUtil fileUtil) {
        this.prefs = prefs;
        this.fileUtil = fileUtil;
    }

    public Drawable filterColor(Drawable drawable, @ColorInt int color) {
        if (drawable == null) {
            return null;
        }

        drawable = drawable.mutate();
        drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
        return drawable;
    }

    public Drawable filterColor(Drawable drawable, @ColorInt int color, HighlightAnnotationStyle style) {
        if (drawable == null) {
            return null;
        }

        drawable = drawable.mutate();
        drawable.setColorFilter(new PorterDuffColorFilter(color, style == HighlightAnnotationStyle.FILL ? PorterDuff.Mode.SRC_ATOP : PorterDuff.Mode.MULTIPLY));
        return drawable;
    }

    public Drawable invertColors(@NonNull Drawable drawable) {
        drawable.setColorFilter(NEGATIVE_COLOR_MATRIX);
        return drawable;
    }

    public String getBaseImageUrl() {
        return prefs.getContentServerType().getContentUrl();
    }

    public Bitmap createBitmapThumb(long screenId, Activity activity) {
        if (activity == null) {
            return null;
        }

        // try to get the view WITHOUT the actionbar
        View captureView = activity.findViewById(R.id.topLayout);

        if (captureView == null) {
            // fallback to the whole screen
            captureView = activity.getWindow().getDecorView();
        }

        Bitmap bmp = null;
        try {
            bmp = createViewBitmap(captureView, getThumbSize(activity, false));
            if (bmp != null) {
                int scaledWidth = activity.getResources().getDimensionPixelSize(R.dimen.tab_thumb_width);
                int scaledHeight = activity.getResources().getDimensionPixelSize(R.dimen.tab_thumb_height);
                bmp = Bitmap.createScaledBitmap(bmp, scaledWidth, scaledHeight, true);
            }
        } catch (Error e) { // NOSONAR - Creating the bitmap can cause a OutOfMemoryError... we want to catch this gracefully
            Timber.e(e, "Unable to create bitmap");
        }

        if (bmp == null) {
            try {
                bmp = BitmapFactory.decodeResource(activity.getResources(), R.drawable.tab_default);
            } catch (Exception e) {
                Timber.e(e, "Exception decoding bitmap");
            }
        }

        if (bmp == null) {
            return null;
        }

        FileOutputStream out = null;
        try {
            File file = fileUtil.getThumbsFile(screenId);
            out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, SCREEN_SHOT_QUALITY, out);
        } catch (Exception e) {
            Timber.e(e, "Exception saving bitmap");
        } finally {
            IOUtils.closeQuietly(out);
        }
        try {
            bmp.recycle();
            bmp = null;
        } catch (Exception e) {
            Timber.e(e, "Exception recycling bitmap");
        }

        return bmp;
    }

    private int getThumbSize(Activity activity, boolean shrink) {
        Rect r = new Rect();
        View decorView = activity.getWindow().getDecorView();
        decorView.getWindowVisibleDisplayFrame(r);
        int bHeight = r.height();
        int bWidth = r.width();

        // take the shorter of the two sides
        int side = bHeight > bWidth ? bWidth : bHeight;

        if (shrink) {
            side *= THUMB_SCALE_PERCENT;
        }

        return side;
    }

    @Nullable
    private Bitmap createViewBitmap(View view, int size) {
        int padding = view.getPaddingTop();
        int widthHeight = size + padding;

        if (widthHeight == 0) {
            Timber.w("Could not create view bitmap because size was 0");
            return null;
        }

        if (widthHeight > view.getHeight()) {
            size = view.getHeight() - padding;
        }

        Bitmap bitmap = Bitmap.createBitmap(widthHeight, widthHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);

        //Draws the correct background color first
        int colorInt = LdsDrawableUtil.INSTANCE.resolvedColorIntResourceId(view.getContext(), R.attr.themeStyleColorListBackground);
        canvas.drawColor(colorInt);

        //Draws the view
        view.draw(canvas);

        // handle top padding, if necessary. (this is for when the actionbar is overlay style.)
        if (padding > 0) {
            bitmap = Bitmap.createBitmap(bitmap, 0, padding, size, size);
        }

        return bitmap;
    }
}