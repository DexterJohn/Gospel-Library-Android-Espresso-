package org.lds.ldssa.ui.adapter.viewholder;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A Simple ViewHolder for the list_item_radio layout
 */
public class ListItemRadioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public interface OnCheckChangedListener {
        void onCheckChanged(int position, boolean checked);
    }

    @BindView(R.id.list_item_radio_button)
    RadioButton radioButton;
    @BindView(R.id.list_item_radio_text)
    TextView textView;

    @Nullable
    private OnCheckChangedListener checkChangedListener;
    private boolean isChecked = false;
    private int position = -1;

    public static ListItemRadioViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_radio, parent, false);
        return new ListItemRadioViewHolder(view);
    }

    public ListItemRadioViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        itemView.setOnClickListener(this);
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setOnCheckChangedListener(OnCheckChangedListener listener) {
        this.checkChangedListener = listener;
    }

    public void setText(String text) {
        textView.setText(text);
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        radioButton.setChecked(checked);
    }

    @Override
    public void onClick(View view) {
        if (checkChangedListener != null) {
            setChecked(true);
            checkChangedListener.onCheckChanged(position, isChecked);
        }
    }
}