package org.lds.ldssa.model.navigation.sidebar;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;
import org.lds.ldssa.model.navigation.InvalidExtraException;

import java.util.ArrayList;
import java.util.List;

public class SideBarHistoryExtrasRelatedContent extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_CONTENT_ITEM_ID = "contentItemId";
    private static final String EXTRA_KEY_SUB_ITEM_ID = "subItemId";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private long contentItemId;
    private long subItemId;
    private int scrollPosition;

    public SideBarHistoryExtrasRelatedContent(long contentItemId, long subItemId, int scrollPosition) {
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.scrollPosition = scrollPosition;
    }

    public SideBarHistoryExtrasRelatedContent(Gson gson, String extrasJson) {
        deserialize(gson, extrasJson);
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTENT_ITEM_ID, contentItemId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SUB_ITEM_ID, subItemId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_CONTENT_ITEM_ID:
                    contentItemId = dtoNavigationHistoryItemExtra.getValueAsLong();
                    break;
                case EXTRA_KEY_SUB_ITEM_ID:
                    subItemId = dtoNavigationHistoryItemExtra.getValueAsLong();
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
        if (subItemId <= 0) {
            throw new InvalidExtraException(EXTRA_KEY_SUB_ITEM_ID, subItemId);
        }
        if (scrollPosition < 0) {
            throw new InvalidExtraException(EXTRA_KEY_SCROLL_POSITION, scrollPosition);
        }
    }

    public long getContentItemId() {
        return contentItemId;
    }

    public void setContentItemId(long contentItemId) {
        this.contentItemId = contentItemId;
    }

    public long getSubItemId() {
        return subItemId;
    }

    public void setSubItemId(long subItemId) {
        this.subItemId = subItemId;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
