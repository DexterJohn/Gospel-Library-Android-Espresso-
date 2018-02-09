package org.lds.ldssa.ui.adapter.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.lds.ldssa.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NavigationTrailViewHolder  extends ClickableViewHolder {
    @BindView(R.id.navigation_trail_title)
    TextView titleView;

    public static NavigationTrailViewHolder newInstance(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_navigation_trail, parent, false);
        return new NavigationTrailViewHolder(view);
    }

    public NavigationTrailViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void setTitle(CharSequence title) {
        titleView.setText(title);
    }

    public void setPosition(int position) {
        itemView.setTag(position);
    }
}