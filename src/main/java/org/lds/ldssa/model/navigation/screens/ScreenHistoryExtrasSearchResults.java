package org.lds.ldssa.model.navigation.screens;

import org.lds.ldssa.model.database.types.SearchLevel;
import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasSearchResults extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_SEARCH_TEXT = "searchText";
    private static final String EXTRA_KEY_SEARCH_LEVEL = "searchLevel";
    private static final String EXTRA_KEY_SECTION_ID = "sectionId";
    private static final String EXTRA_KEY_ITEM_ID = "itemId";
    private static final String EXTRA_KEY_SCROLL_POSITION = "scrollPosition";

    private String searchText;
    private SearchLevel searchLevel;
    private long sectionId;
    private long itemId;
    private int scrollPosition;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasSearchResults() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasSearchResults(String searchText, SearchLevel searchLevel, long sectionId, long itemId, int scrollPosition) {
        this.searchText = searchText;
        this.searchLevel = searchLevel;
        this.sectionId = sectionId;
        this.itemId = itemId;
        this.scrollPosition = scrollPosition;
    }

    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SEARCH_TEXT, searchText));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SEARCH_LEVEL, searchLevel.ordinal()));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SECTION_ID, sectionId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_ITEM_ID, itemId));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_SCROLL_POSITION, scrollPosition));

        return extrasList;
    }

    public void setExtras(List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_SEARCH_TEXT:
                    searchText = dtoNavigationHistoryItemExtra.getValue();
                    break;
                case EXTRA_KEY_SEARCH_LEVEL:
                    searchLevel = SearchLevel.values()[dtoNavigationHistoryItemExtra.getValueAsInt()];
                    break;
                case EXTRA_KEY_SECTION_ID:
                    sectionId = dtoNavigationHistoryItemExtra.getValueAsInt();
                    break;
                case EXTRA_KEY_ITEM_ID:
                    itemId = dtoNavigationHistoryItemExtra.getValueAsLong();
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
        if (searchLevel == null) {
            throw new InvalidExtraException(EXTRA_KEY_SEARCH_LEVEL, "null");
        }
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public SearchLevel getSearchLevel() {
        return searchLevel;
    }

    public void setSearchLevel(SearchLevel searchLevel) {
        this.searchLevel = searchLevel;
    }

    public long getSectionId() {
        return sectionId;
    }

    public void setSectionId(long sectionId) {
        this.sectionId = sectionId;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getScrollPosition() {
        return scrollPosition;
    }

    public void setScrollPosition(int scrollPosition) {
        this.scrollPosition = scrollPosition;
    }
}
