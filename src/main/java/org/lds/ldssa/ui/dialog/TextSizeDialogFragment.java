package org.lds.ldssa.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.lds.ldssa.R;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.model.prefs.type.ContentTextSizeType;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketknife.PocketKnife;

public class TextSizeDialogFragment extends AppCompatDialogFragment implements OnSeekBarChangeListener {

    @Inject
    Prefs prefs;

    @BindView(R.id.text)
    TextView sampleTextView;
    @BindView(R.id.seekbar)
    SeekBar textSizeBar;

    private ContentTextSizeType contentTextSizeType;
    private boolean saveChange = false;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Injector.INSTANCE.get().inject(this);
        PocketKnife.bindArguments(this);

        contentTextSizeType = prefs.getContentTextSize();
        applyTheme();

        return new MaterialDialog.Builder(getContext())
                .title(R.string.text_size)
                .customView(getContentView(), false)
                .positiveText(R.string.ok)
                .onPositive((materialDialog, dialogAction) -> {
                    saveChange = true;
                    dismiss();
                })
                .negativeText(R.string.cancel)
                .build();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (saveChange) {
            prefs.setContentTextSize(contentTextSizeType);
        }

        super.onDismiss(dialog);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            contentTextSizeType = ContentTextSizeType.get(progress);
            sampleTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, contentTextSizeType.getPixelSize());
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Purposefully left blank
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        //Purposefully left blank
    }

    private void applyTheme() {
        switch (prefs.getGeneralDisplayTheme()) {
            case SEPIA:
                getContext().setTheme(R.style.AppTheme_Sepia);
                break;
            case DARK:
                getContext().setTheme(R.style.AppTheme_Dark);
                break;
            case DARK_BLUE:
                getContext().setTheme(R.style.AppTheme_DarkBlue);
                break;
            case MAGENTA:
                getContext().setTheme(R.style.AppTheme_Magenta);
                break;
            default:
                getContext().setTheme(R.style.AppTheme_Light);
                break;
        }
    }

    /**
     * Creates the content view and performs any necessary setup.
     *
     * @return The resulting View
     */
    @SuppressLint("InflateParams")
    private View getContentView() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.dialog_font_size, null);
        ButterKnife.bind(this, view);

        textSizeBar.setMax(ContentTextSizeType.values().length - 1);
        textSizeBar.setKeyProgressIncrement(1);

        textSizeBar.setProgress(contentTextSizeType.ordinal());
        textSizeBar.setOnSeekBarChangeListener(this);

        //NOTE: the sample text is from Alma 37:6
        sampleTextView.setText(getContext().getResources().getString(R.string.pref_font_size_sample));
        sampleTextView.setTextSize(contentTextSizeType.getPixelSize());

        return view;
    }
}