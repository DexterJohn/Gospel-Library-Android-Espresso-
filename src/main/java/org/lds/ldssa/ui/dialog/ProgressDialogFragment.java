package org.lds.ldssa.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.lds.ldssa.R;
import org.lds.mobile.util.LdsTagUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketknife.BindArgument;
import pocketknife.PocketKnife;

public class ProgressDialogFragment extends AppCompatDialogFragment {
    private static final String TAG = LdsTagUtil.createTag(ProgressDialogFragment.class);
    private static final String ARG_TITLE_ID = "ARG_TITLE_ID";
    private static final String ARG_MESSAGE_ID = "ARG_MESSAGE_ID";
    private static final String ARG_INDETERMINATE = "ARG_INDETERMINATE";

    @BindView(R.id.progress_dialog_message)
    TextView messageView;
    @BindView(R.id.progress_dialog_progress)
    ProgressBar progressBar;

    @StringRes
    @BindArgument(ARG_TITLE_ID)
    int titleId;

    @StringRes
    @BindArgument(ARG_MESSAGE_ID)
    int messageId;

    @BindArgument(ARG_INDETERMINATE)
    boolean indeterminate;

    public static ProgressDialogFragment newInstance(@StringRes int title, @StringRes int message, boolean indeterminate) {
        ProgressDialogFragment dialog = new ProgressDialogFragment();

        dialog.setCancelable(false);

        Bundle args = new Bundle();
        args.putInt(ARG_TITLE_ID, title);
        args.putInt(ARG_MESSAGE_ID, message);
        args.putBoolean(ARG_INDETERMINATE, indeterminate);
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        PocketKnife.bindArguments(this);

        //Creates the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(getContentView(getActivity().getLayoutInflater()));

        //If we have a valid title, then set it
        if (titleId != 0) {
            builder.setTitle(titleId);
        }

        return builder.create();
    }

    /**
     * A simplified way to show the dialog without passing a Tag
     *
     * @param fragmentManager The fragment manager to show the dialog with
     */
    public void show(FragmentManager fragmentManager) {
        show(fragmentManager, TAG);
    }

    /**
     * Creates the content view and performs any necessary setup.
     *
     * @param inflater The LayoutInflater to use when creating the view
     * @return The resulting View
     */
    @SuppressLint("InflateParams")
    private View getContentView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_progress, null);
        ButterKnife.bind(this, view);

        messageView.setText(messageId);
        progressBar.setIndeterminate(indeterminate);

        return view;
    }
}