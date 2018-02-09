package org.lds.ldssa.model.navigation.screens;

import com.crashlytics.android.Crashlytics;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class ScreenHistoryExtrasCatalogDirectory extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_COLLECTION_ID = "collectionId";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private long collectionId;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasCatalogDirectory() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasCatalogDirectory(long collectionId, int scrollPosition) {
        this.collectionId = collectionId;
        this.scrollPosition = scrollPosition;
    }

    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_COLLECTION_ID, collectionId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_COLLECTION_ID:
                    collectionId = dtoNavigationHistoryItemExtra.getValueAsLong();
                    break;
                case EXTRA_KEY_SCROLL_POSITION:
                    scrollPosition = dtoNavigationHistoryItemExtra.getValueAsInt();
                    break;
                default:
                    // ignore extra extras
            }
        }

        verifyRequiredExtras();
    }

    private void verifyRequiredExtras() {
        if (collectionId <= 0) {
            // this should be fine....
            Crashlytics.log(1, "ScreenHistoryExtrasCatalogDirectory - verifyRequiredExtras - bad collectionId", "EXTRA_KEY_SCROLL_POSITION: [" + scrollPosition + "]  EXTRA_KEY_COLLECTION_ID:  [ " + collectionId + "]");
            Timber.e("ScreenHistoryExtrasCatalogDirectory - verifyRequiredExtras - bad collectionId");
//            throw new InvalidExtraException(EXTRA_KEY_COLLECTION_ID, collectionId);
        }
    }

    public long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
