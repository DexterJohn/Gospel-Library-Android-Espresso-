package org.lds.ldssa.task;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.lds.ldssa.event.content.ContentLinkedEvent;
import org.lds.ldssa.model.database.content.subitem.SubItem;
import org.lds.ldssa.model.database.content.subitem.SubItemManager;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;
import org.lds.ldssa.model.database.userdata.link.LinkManager;
import org.lds.ldssa.util.CitationUtil;
import org.lds.ldssa.util.annotations.LinkUtil;
import org.lds.mobile.task.RxTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import pocketbus.Bus;

public class LinkSubItemTask extends RxTask<Boolean> {
    private static final String DELIMINATOR = " ";
    private static final int OFFSET_SIZE = 4;
    private static final String PARAGRAPH_ID_ATTR = "data-aid";
    private static final String START_PARAGRAPH = "<p";
    private static final String END_PARAGRAPH = "</p>";
    private static final String PARAGRAPH = "p";
    private static final List<String> REFERENCE_CLASSES = Arrays.asList("reference", "short-reference");

    private final SubItemContentManager subItemContentManager;
    private final LinkManager linkManager;
    private final SubItemManager subItemManager;
    private final CitationUtil citationUtil;
    private final Bus bus;
    private final LinkUtil linkUtil;

    private FragmentActivity contextActivity;
    private String queryText;
    private long contentItemId;
    private long subItemId;
    private long annotationId;
    private long screenId;
    private List<String> paragraphAids = new ArrayList<>();

    @Inject
    public LinkSubItemTask(SubItemContentManager subItemContentManager, LinkManager linkManager, SubItemManager subItemManager, CitationUtil citationUtil, Bus bus, LinkUtil linkUtil) {
        this.subItemContentManager = subItemContentManager;
        this.linkManager = linkManager;
        this.subItemManager = subItemManager;
        this.citationUtil = citationUtil;
        this.bus = bus;
        this.linkUtil = linkUtil;
    }

    public LinkSubItemTask init(FragmentActivity contextActivity, long screenId, long contentItemId, long subItemId, long annotationId, String queryText) {
        return init(contextActivity, screenId, contentItemId, subItemId, annotationId, null, queryText);
    }

    public LinkSubItemTask init(FragmentActivity contextActivity, long screenId, long contentItemId,
                                long subItemId, long annotationId, @Nullable String paragraphAid, @Nullable String queryText) {
        this.contextActivity = contextActivity;
        this.screenId = screenId;
        this.contentItemId = contentItemId;
        this.subItemId = subItemId;
        this.annotationId = annotationId;
        this.queryText = queryText;
        if (!StringUtils.isEmpty(paragraphAid)) {
            paragraphAids.add(paragraphAid);
        }
        return this;
    }

    @NonNull
    @Override
    protected Boolean run() {
        if (contextActivity == null || StringUtils.isEmpty(queryText)) {
            return false;
        }

        final SubItem subItem = subItemManager.findByRowId(contentItemId, subItemId);
        if (subItem == null) {
            return false;
        }

        final String content = subItemContentManager.findContentById(contentItemId, subItemId);
        if (paragraphAids.isEmpty()) {
            findParagraphIds(content, paragraphAids);
        }

        if (paragraphAids.isEmpty()) {
            return false;
        }

        linkManager.beginTransaction();
        for (String paragraphAid : paragraphAids) {
            linkUtil.add(annotationId, subItem.getDocId(), Collections.singletonList(paragraphAid), citationUtil.createCitationText(contentItemId, subItemId, paragraphAid));
        }
        linkManager.endTransaction(true);

        return true;
    }

    @Override
    protected void onResult(Boolean contentLinked) {
        if (contentLinked) {
            bus.post(new ContentLinkedEvent(screenId, annotationId, contentItemId, subItemId, paragraphAids));
        }
    }

    private void findParagraphIds(String content, List<String> paragraphIds) {
        final Elements elements = Jsoup.parse(content).getElementsByTag(PARAGRAPH);
        Element element;
        for (int index = 0; index < elements.size(); index++) {
            element = elements.get(index);
            String pid = element.attr(PARAGRAPH_ID_ATTR);
            if (!REFERENCE_CLASSES.contains(element.className()) && !paragraphIds.contains(pid)) {
                paragraphIds.add(pid);
                return;
            }
        }
    }

    private void findParagraphIdsForScripture(long languageId, String content, List<String> paragraphIds) { // NOSONAR Not being used right now but that could change
//        final String offsetString = subItemSearchQueryManager.findTextOffsets(contentItemId, subItemId,
//                searchUtil.createSearchRequest(queryText, languageId));
//        if (StringUtils.isEmpty(offsetString)) {
//            return;
//        }
//
//        final String[] offsets = offsetString.split(DELIMINATOR);
//        if (offsets.length == 0) {
//            return;
//        }
//
//        int position;
//        for (int index = 2; index < offsets.length; index += OFFSET_SIZE) {
//            position = Integer.parseInt(offsets[index]);
//            findParagraphIds(content.substring(
//                    contentParagraphUtil.findMatchingIndex(START_PARAGRAPH, position, content, false, true),
//                    contentParagraphUtil.findMatchingIndex(END_PARAGRAPH, position, content, true, false)), paragraphIds);
//        }
    }
}
