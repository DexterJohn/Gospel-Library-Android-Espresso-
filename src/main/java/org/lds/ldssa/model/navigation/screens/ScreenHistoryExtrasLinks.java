package org.lds.ldssa.model.navigation.screens;

import org.lds.ldssa.model.navigation.DtoNavigationHistoryItemExtra;
import org.lds.ldssa.model.navigation.InvalidExtraException;
import org.lds.ldssa.model.navigation.NavigationHistoryItemExtras;

import java.util.ArrayList;
import java.util.List;

public class ScreenHistoryExtrasLinks extends NavigationHistoryItemExtras {
    private static final String EXTRA_KEY_ANNOTATION_ID = "annotationId";

    private long annotationId;

    @SuppressWarnings("unused")
    public ScreenHistoryExtrasLinks() {
        // used by ScreenHistoryItem.getExtras()
    }

    public ScreenHistoryExtrasLinks(long annotationId) {
        this.annotationId = annotationId;
    }

    public List<DtoNavigationHistoryItemExtra> getExtras() {
        verifyRequiredExtras();

        List<DtoNavigationHistoryItemExtra> extrasList = new ArrayList<>();
        extrasList.add(new DtoNavigationHistoryItemExtra(EXTRA_KEY_ANNOTATION_ID, annotationId));

        return extrasList;
    }

    public void setExtras(List<DtoNavigationHistoryItemExtra> extrasList) {
        for (DtoNavigationHistoryItemExtra dtoNavigationHistoryItemExtra : extrasList) {
            switch (dtoNavigationHistoryItemExtra.getKey()) {
                case EXTRA_KEY_ANNOTATION_ID:
                    annotationId = dtoNavigationHistoryItemExtra.getValueAsInt();
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
