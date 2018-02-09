package org.lds.ldssa.ui.widget;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.MediaRouteActionProvider;
import android.support.v7.app.MediaRouteButton;
import android.support.v7.app.MediaRouteChooserDialog;
import android.support.v7.app.MediaRouteChooserDialogFragment;
import android.support.v7.app.MediaRouteControllerDialog;
import android.support.v7.app.MediaRouteControllerDialogFragment;
import android.support.v7.app.MediaRouteDialogFactory;
import android.util.AttributeSet;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.mobile.media.cast.CastManager;

import javax.inject.Inject;
/**
 * A MediaRoute button that will correctly hide and show based on the
 * Chromecast device availability.
 */
public class LDSCastButton extends MediaRouteButton {

    @Inject
    CastManager castManager;

    public LDSCastButton(Context context) {
        super(context);
        init(context);
    }

    public LDSCastButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LDSCastButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        Injector.INSTANCE.get().inject(this);
        castManager.setupCastButton(context, this);
        setDialogFactory(new ThemeCompliantDialogFactory());
    }

    /**
     * A dialog factory that correctly abides by the apps theme.  The default implementation
     * will only pay attention to the theme being light or dark, not to the colors used for text,
     * icons, backgrounds, etc.
     */
    public static class ThemeCompliantDialogFactory extends MediaRouteDialogFactory {
        @Inject
        Prefs prefs;

        public ThemeCompliantDialogFactory() {
            super();
            Injector.INSTANCE.get().inject(this);
        }

        @NonNull
        @Override
        public MediaRouteChooserDialogFragment onCreateChooserDialogFragment() {
            return new ThemeCompliantRouteChooserDialogFragment();
        }

        @NonNull
        @Override
        public MediaRouteControllerDialogFragment onCreateControllerDialogFragment() {
            return new ThemeCompliantRouteControllerDialogFragment();
        }
    }

    /**
     * Used to provide a correctly themed RouteChooserDialog that correctly
     * follows the apps theme.  This is needed because the default implementation
     * ignores the theme values for many of the fields
     */
    public static class ThemeCompliantRouteChooserDialogFragment extends MediaRouteChooserDialogFragment {
        @Override
        public MediaRouteChooserDialog onCreateChooserDialog(Context context, Bundle savedInstanceState) {
            return new MediaRouteChooserDialog(context, R.style.AppTheme_Light_MediaRoute_Chooser);
        }
    }

    /**
     * Used to provide a correctly themed RouteControllerDialog that correctly
     * follows the apps theme.  This is needed because the default implementation
     * ignores the theme values for many of the fields
     */
    public static class ThemeCompliantRouteControllerDialogFragment extends MediaRouteControllerDialogFragment {
        @Override
        public MediaRouteControllerDialog onCreateControllerDialog(Context context, Bundle savedInstanceState) {
            return new MediaRouteControllerDialog(context, R.style.AppTheme_Light_MediaRoute_ControlsPanel);
        }
    }

    @SuppressWarnings("unused") //Referenced in menu_video.xml
    public static class ThemeCompliantActionProvider extends MediaRouteActionProvider {
        public ThemeCompliantActionProvider(Context context) {
            super(context);
            setDialogFactory(new ThemeCompliantDialogFactory());
        }
    }
}
