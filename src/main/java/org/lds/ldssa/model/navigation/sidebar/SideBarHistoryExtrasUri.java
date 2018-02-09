package org.lds.ldssa.model.navigation.sidebar;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class SideBarHistoryExtrasUri extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_TITLE = "title";
    private static final String EXTRA_KEY_URI = "uri";

    private String title;
    private String uri;

    public SideBarHistoryExtrasUri(String title, String uri) {
        this.title = title;
        this.uri = uri;
    }

    public SideBarHistoryExtrasUri(Gson gson, String extrasJson) {
        deserialize(gson, extrasJson);
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_TITLE, title));
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_URI, uri));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_TITLE:
                    title = dtoNavigationHistoryItemExtra.getValue();
                    break;
                case EXTRA_KEY_URI:
                    uri = dtoNavigationHistoryItemExtra.getValue();
                    break;
                default:
                    // ignore extra extras
            }
        }

        verifyRequiredExtras();
    }

    private void verifyRequiredExtras() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}
