package org.lds.ldssa.model.navigation.screens;

import android.support.annotation.NonNull;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasAnnotations extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_NOTEBOOK_ID = "notebookId";
    private static final String EXTRA_KEY_TAG = "tag";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private long notebookId;
    private String tag;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasAnnotations() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasAnnotations(long notebookId, String tag, int scrollPosition) {
        this.notebookId = notebookId;
        this.tag = tag;
        this.scrollPosition = scrollPosition;
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_NOTEBOOK_ID, notebookId));
        if (tag != null) {
            extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_TAG, tag));
        }
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_NOTEBOOK_ID:
                    notebookId = dtoNavigationHistoryItemExtra.getValueAsLong();
                    break;
                case EXTRA_KEY_TAG:
                    tag = dtoNavigationHistoryItemExtra.getValue();
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
        if (notebookId < 0) {
            throw new InvalidExtraException(EXTRA_KEY_NOTEBOOK_ID, notebookId);
        }
        if (scrollPosition < 0) {
            throw new InvalidExtraException(EXTRA_KEY_SCROLL_POSITION, scrollPosition);
        }
    }

    public long getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(long notebookId) {
        this.notebookId = notebookId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
