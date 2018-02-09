package org.lds.ldssa.util;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;

import org.dbtools.android.domain.database.contentvalues.DBToolsContentValues;
import org.lds.ldssa.R;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollection;
import org.lds.ldssa.model.database.userdata.customcollection.CustomCollectionManager;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItem;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemConst;
import org.lds.ldssa.model.database.userdata.customcollectionitem.CustomCollectionItemManager;

import java.util.Map;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CustomCollectionUtil {

    private final Application application;
    private final CustomCollectionManager customCollectionManager;
    private final CustomCollectionItemManager customCollectionItemManager;

    @Inject
    public CustomCollectionUtil(Application application, CustomCollectionManager customCollectionManager, CustomCollectionItemManager customCollectionItemManager) {
        this.application = application;
        this.customCollectionManager = customCollectionManager;
        this.customCollectionItemManager = customCollectionItemManager;
    }

    public void promptAddCustomCollection(@Nonnull AppCompatActivity activity) {
        showCustomCollectionDialog(activity, -1L, "");
    }

    public void promptEditCustomCollectionName(AppCompatActivity activity, long collectionId, @Nonnull String currentTitle) {
        showCustomCollectionDialog(activity, collectionId, currentTitle);
    }

    public void promptDeleteCollection(@Nonnull AppCompatActivity activity, final long customCollectionId) {
        new MaterialDialog.Builder(activity)
                .title(R.string.delete)
                .content(application.getString(R.string.delete_collection_item_title, customCollectionManager.findTitleById(customCollectionId)))
                .positiveText(R.string.delete)
                .negativeText(R.string.cancel)
                .onPositive((materialDialog, dialogAction) -> customCollectionManager.delete(customCollectionId))
                .show();
    }

    public void removeItemFromCollection(long customCollectionItemId) {
        customCollectionItemManager.delete(customCollectionItemId);
    }

    public int addItemsToCollection(long customCollectionId, String[] catalogItemExternalIds) {
        int maxOrderPosition = customCollectionItemManager.findMaxOrderPosition(customCollectionId);
        int count = 0;
        customCollectionManager.beginTransaction();
        for (String catalogItemExternalId : catalogItemExternalIds) {
            maxOrderPosition++;
            CustomCollectionItem item = new CustomCollectionItem();
            item.setCustomCollectionId(customCollectionId);
            item.setCatalogItemExternalId(catalogItemExternalId);
            item.setOrderPosition(maxOrderPosition);
            if (customCollectionItemManager.insert(item) > 0) {
                count++;
            }
        }
        customCollectionManager.endTransaction(true);
        customCollectionManager.updateCollectionLastModified(customCollectionId);
        return count;
    }

    public void updateOrderPositions(long customCollectionId, Map<Long, Integer> positions) {
        DBToolsContentValues values = customCollectionItemManager.createNewDBToolsContentValues();
        customCollectionItemManager.beginTransaction();

        for (Map.Entry<Long, Integer> entry : positions.entrySet()) {
            values.put(CustomCollectionItemConst.C_ORDER_POSITION, entry.getValue());
            customCollectionItemManager.update(values, entry.getKey());
        }

        customCollectionItemManager.endTransaction(true);
        customCollectionManager.updateCollectionLastModified(customCollectionId);
    }

    private void showCustomCollectionDialog(AppCompatActivity activity, long collectionId, @Nonnull String inputText) {
        int okResId = collectionId >= 0 ? R.string.ok : R.string.add;

        new MaterialDialog.Builder(activity)
                .title(R.string.collection_name)
                .positiveText(okResId)
                .negativeText(R.string.cancel)
                .input(null, inputText, new CustomCollectionTextListener(collectionId))
                .show();
    }

    private class CustomCollectionTextListener implements MaterialDialog.InputCallback {
        private long collectionId = -1L;

        public CustomCollectionTextListener(long collectionId) {
            this.collectionId = collectionId;
        }

        @Override
        public void onInput(@NonNull MaterialDialog materialDialog, CharSequence text) {
            if (collectionId < 0) {
                addCollection(text.toString());
            } else {
                updateCollection(collectionId, text.toString());
            }
        }
    }

    private void addCollection(String name) {
        CustomCollection collection = new CustomCollection(name);
        customCollectionManager.save(collection);
    }

    private void updateCollection(long collectionId, String newName) {
        customCollectionManager.updateCollection(collectionId, newName);
    }

    public void updateCollectionPositionOrder(Map<Long, Integer> positionMap) {
        customCollectionManager.updateOrderPositions(positionMap);
    }
}
