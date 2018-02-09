package org.lds.ldssa.ui.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.devbrackets.android.recyclerext.adapter.RecyclerListAdapter;
import com.devbrackets.android.recyclerext.adapter.viewholder.ClickableViewHolder;

import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.catalog.navigationtrailquery.NavigationTrailQuery;
import org.lds.ldssa.ui.adapter.viewholder.NavigationTrailViewHolder;

import java.util.List;

public class NavigationTrailAdapter extends RecyclerListAdapter<NavigationTrailViewHolder, NavigationTrailQuery>
        implements ClickableViewHolder.OnClickListener {

    public interface OnNavigationTrailClickListener {
        void onNavigationTrailClick(NavigationTrailQuery query);
    }

    @Nullable
    private OnNavigationTrailClickListener listener;

    public NavigationTrailAdapter() {
        Injector.INSTANCE.get().inject(this);
    }

    @Override
    @SuppressLint("InflateParams")
    public NavigationTrailViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        NavigationTrailViewHolder holder = NavigationTrailViewHolder.newInstance(viewGroup);
        holder.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(NavigationTrailViewHolder holder, int position) {
        NavigationTrailQuery query = getItem(position);
        if (query == null) {
            return;
        }

        holder.setTitle(query.getTitle());
        holder.setPosition(position);
    }

    @Override
    public void onClick(@NonNull ClickableViewHolder clickableViewHolder) {
        if (listener != null) {
            NavigationTrailQuery navigationTrailQuery = getItem(clickableViewHolder.getAdapterPosition());
            if (navigationTrailQuery != null) {
                listener.onNavigationTrailClick(navigationTrailQuery);
            }
        }
    }

    public void changeList(List<NavigationTrailQuery> queries) {
        clear();
        addAll(queries);
    }

    public void setOnNavigationTrailClickListener(@Nullable OnNavigationTrailClickListener listener) {
        this.listener = listener;
    }
}