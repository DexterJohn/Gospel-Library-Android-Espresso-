package org.lds.ldssa.ui.menu;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.R;
import org.lds.ldssa.ui.activity.BaseActivity;
import org.lds.ldssa.util.CustomCollectionUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomCollectionDirectoryMenu {
    private final CommonMenu commonMenu;
    private final InternalIntents internalIntents;
    private final CustomCollectionUtil customCollectionUtil;

    @Inject
    public CustomCollectionDirectoryMenu(CommonMenu commonMenu, InternalIntents internalIntents, CustomCollectionUtil customCollectionUtil) {
        this.commonMenu = commonMenu;
        this.internalIntents = internalIntents;
        this.customCollectionUtil = customCollectionUtil;
    }

    public void onCreateOptionsMenu(Context context, Menu menu, MenuInflater inflater) {
        commonMenu.inflateMenuPre(context, menu, inflater);
        inflater.inflate(R.menu.menu_catalog_directory, menu);
        inflater.inflate(R.menu.menu_catalog_crud, menu);
        commonMenu.inflateMenuPost(context, menu, inflater);
    }

    public boolean onOptionsItemSelected(BaseActivity baseActivity, MenuItem menuItem, long customCollectionId) {
        return onCustomCollectionsOptionsItemSelected(baseActivity, menuItem, customCollectionId);

    }
    private boolean onCustomCollectionsOptionsItemSelected(BaseActivity baseActivity, MenuItem item, long collectionId) {
        switch (item.getItemId()) {
            case R.id.menu_item_manage_custom_collection:
                internalIntents.showManageCustomCollections(baseActivity, baseActivity.getScreenId());
                return true;
            case R.id.menu_item_delete_custom_collection:
                customCollectionUtil.promptDeleteCollection(baseActivity, collectionId);
                return true;
            default:
                return false;
        }
    }
}
