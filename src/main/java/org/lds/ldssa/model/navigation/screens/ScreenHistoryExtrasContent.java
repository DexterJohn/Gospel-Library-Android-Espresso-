package org.lds.ldssa.model.navigation.screens;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasContent extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_CONTENT_ITEM_ID = "contentItemId";
    private static final String EXTRA_KEY_SUB_ITEM_URI = "subItemUri";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private long contentItemId;
    private String subItemUri;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasContent() {
        // used by ScreenHistoryItem.getExtras()
    }


    public ScreenHistoryExtrasContent(long contentItemId, String subItemUri, int scrollPosition) {
        this.contentItemId = contentItemId;
        this.subItemUri = subItemUri;
        this.scrollPosition = scrollPosition;
    }

    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_CONTENT_ITEM_ID, contentItemId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SUB_ITEM_URI, subItemUri));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_CONTENT_ITEM_ID:
                    contentItemId = dtoNavigationHistoryItemExtra.getValueAsLong();
                    break;
                case EXTRA_KEY_SUB_ITEM_URI:
                    subItemUri = dtoNavigationHistoryItemExtra.getValue();
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
        if (StringUtils.isBlank(subItemUri)) {
            // Added extra logging to help identify why we are getting into this state on 2/24/2017 (ContentActivity.java:235,918)
            throw new InvalidExtraException(EXTRA_KEY_SUB_ITEM_URI, subItemUri);
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

    public String getSubItemUri() {
        return subItemUri;
    }

    public void setSubItemUri(String subItemUri) {
        this.subItemUri = subItemUri;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
