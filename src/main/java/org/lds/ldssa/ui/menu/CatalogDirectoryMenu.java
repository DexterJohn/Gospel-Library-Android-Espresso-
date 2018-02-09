package org.lds.ldssa.ui.menu;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.catalog.language.LanguageManager;
import org.lds.ldssa.ui.activity.BaseActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CatalogDirectoryMenu {
    private final CommonMenu commonMenu;
    private final InternalIntents internalIntents;
    private final LanguageManager languageManager;

    @Inject
    public CatalogDirectoryMenu(CommonMenu commonMenu, InternalIntents internalIntents, LanguageManager languageManager) {
        this.commonMenu = commonMenu;
        this.internalIntents = internalIntents;
        this.languageManager = languageManager;
    }

    public void onCreateOptionsMenu(Context context, Menu menu, MenuInflater inflater, long collectionId) {
        boolean rootCollection = languageManager.isRootCollection(collectionId);

        commonMenu.inflateMenuPre(context, menu, inflater);
        inflater.inflate(R.menu.menu_catalog_directory, menu);

        if (!rootCollection) {
            inflater.inflate(R.menu.menu_catalog_crud, menu);
        }

        commonMenu.inflateMenuPost(context, menu, inflater);

        updateMenu(menu, rootCollection);
    }

    public boolean onOptionsItemSelected(BaseActivity baseActivity, MenuItem menuItem) {
        return onCatalogOptionsMenuItemSelected(baseActivity, menuItem);
    }

    private boolean onCatalogOptionsMenuItemSelected(BaseActivity baseActivity, MenuItem menu) {
        switch (menu.getItemId()) {
            case R.id.menu_item_manage_custom_collection:
                internalIntents.showManageCustomCollections(baseActivity, baseActivity.getScreenId());
                return true;
        }

        return false;
    }

    public void updateMenu(Menu optionsMenu, boolean rootCollection) {
        if (optionsMenu == null) {
            return;
        }

        MenuItem manageMenuItem = optionsMenu.findItem(R.id.menu_item_manage_custom_collection);

        if (manageMenuItem != null) {
            manageMenuItem.setVisible(rootCollection);
        }
    }
}
