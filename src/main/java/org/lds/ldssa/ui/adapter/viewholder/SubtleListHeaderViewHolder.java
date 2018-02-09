package org.lds.ldssa.ui.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A ViewHolder for the list_header_subtle layout
 */
public class SubtleListHeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.titleTextView)
    TextView textView;

    public static SubtleListHeaderViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_subtle, parent, false);
        return new SubtleListHeaderViewHolder(view);
    }

    public SubtleListHeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setText(CharSequence text) {
        textView.setText(text);
    }

    public TextView getTextView() {
        return textView;
    }
}
