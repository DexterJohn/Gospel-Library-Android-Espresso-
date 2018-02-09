package org.lds.ldssa.model.navigation.screens;

import android.support.annotation.NonNull;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasContentDirectory extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_CONTENT_ITEM_ID = "contentItemId";
    private static final String EXTRA_KEY_NAV_COLLECTION_URI = "navCollectionUri";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";


    private long contentItemId;
    private String navCollectionUri;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasContentDirectory() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasContentDirectory(long contentItemId, String navCollectionUri, int scrollPosition) {
        this.contentItemId = contentItemId;
        this.navCollectionUri = navCollectionUri;
        this.scrollPosition = scrollPosition;
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTENT_ITEM_ID, contentItemId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_NAV_COLLECTION_URI, navCollectionUri));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_CONTENT_ITEM_ID:
                    contentItemId = dtoNavigationHistoryItemExtra.getValueAsLong();
                    break;
                case EXTRA_KEY_NAV_COLLECTION_URI:
                    navCollectionUri = dtoNavigationHistoryItemExtra.getValue();
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
        if (contentItemId <= 0) {
            throw new InvalidExtraException(EXTRA_KEY_CONTENT_ITEM_ID, contentItemId);
        }
    }

    public long getContentItemId() {
        return contentItemId;
    }

    public void setContentItemId(long contentItemId) {
        this.contentItemId = contentItemId;
    }

    public String getNavCollectionUri() {
        return navCollectionUri;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }

    // For debugging
    public String toString() {
        return String.format("ScreenHistoryExtrasContentDirectory {contentItemId: %d, navCollectionUri = '%s', scrollPosition: %d}", contentItemId, navCollectionUri, scrollPosition);
    }
}
