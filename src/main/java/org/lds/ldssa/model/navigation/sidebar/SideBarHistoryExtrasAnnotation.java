package org.lds.ldssa.model.navigation.sidebar;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;
import org.lds.ldssa.model.navigation.InvalidExtraException;

import java.util.ArrayList;
import java.util.List;

public class SideBarHistoryExtrasAnnotation extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_ANNOTATION_ID = "annotationId";

    private long annotationId;

    public SideBarHistoryExtrasAnnotation(long annotationId) {
        this.annotationId = annotationId;
    }

    public SideBarHistoryExtrasAnnotation(Gson gson, String extrasJson) {
        deserialize(gson, extrasJson);
    }

    @NonNull
    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_ANNOTATION_ID, annotationId));

        return extrasList;
    }

    public void setExtras(@NonNull List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_ANNOTATION_ID:
                    annotationId = dtoNavigationHistoryItemExtra.getValueAsLong();
                    break;
                default:
                    // ignore extra extras
            }
        }

        verifyRequiredExtras();
    }

    private void verifyRequiredExtras() {
        if (annotationId < 0) {
            throw new InvalidExtraException(EXTRA_KEY_ANNOTATION_ID, annotationId);
        }
    }

    public long getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(long annotationId) {
        this.annotationId = annotationId;
    }

}
