package org.lds.ldssa.util;

import android.app.Application;
import android.text.Spanned;

import org.lds.mobile.markdown.parser.html.HtmlParser;
import org.lds.mobile.markdown.parser.markdown.MarkdownParser;

import javax.inject.Inject;

public class MarkdownUtil {

    private Application application;
    private MarkdownParser markdownParser;

    @Inject
    public MarkdownUtil(Application application) {
        this.application = application;

        markdownParser = new MarkdownParser(application);
    }

    public String convertMarkdownToHtml(String markdown) {
        Spanned spanned = markdownParser.toSpanned(markdown);
        HtmlParser htmlParser = new HtmlParser(application);
        return htmlParser.fromSpanned(spanned);
    }
}
