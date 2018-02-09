package org.lds.ldssa.ui.adapter.viewholder;

import android.support.annotation.StringRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A ViewHolder for the list_header_standard layout
 */
public class StandardListHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.list_header_standard_text)
    TextView textView;

    public static StandardListHeaderViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_standard, parent, false);
        return new StandardListHeaderViewHolder(view);
    }

    public StandardListHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public void setText(@StringRes int stringResId) {
        textView.setText(stringResId);
    }

    public TextView getTextView() {
        return textView;
    }
}
