package org.lds.ldssa.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.model.database.content.subitemcontent.SubItemContentManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import pocketknife.BindExtra;
import pocketknife.PocketKnife;

public class ContentSourceActivity extends AppCompatActivity {

    public static final String EXTRA_CONTENT_ITEM_ID = "EXTRA_CONTENT_ITEM_ID";
    public static final String EXTRA_SUB_ITEM_ID = "EXTRA_SUB_ITEM_ID";

    @Inject
    Analytics analytics;
    @Inject
    SubItemContentManager subItemContentManager;

    @BindView(R.id.sourceSearchEditText)
    TextView sourceSearchEditText;
    @BindView(R.id.countTextView)
    TextView countTextView;
    @BindView(R.id.sourceWebView)
    WebView sourceWebView;

    @BindExtra(EXTRA_CONTENT_ITEM_ID)
    long contentItemId;
    @BindExtra(EXTRA_SUB_ITEM_ID)
    long subItemId;

    private String currentSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_source);
        Injector.INSTANCE.get().inject(this);
        ButterKnife.bind(this);
        PocketKnife.bindExtras(this);
        showSource();

        FindCountListener findCountListener = new FindCountListener();
        sourceWebView.setFindListener(findCountListener);

        sourceSearchEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onSearch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        analytics.postScreen(Analytics.Screen.CONTENT_SOURCE);
    }

    private void showSource() {
        sourceWebView.getSettings().setUseWideViewPort(true);

        try {
            String formattedHtml = URLEncoder.encode(
//                    "<style>xmp { white-space: pre-wrap;}</style>" +
                    "<xmp>" +
                    subItemContentManager.findContentById(contentItemId, subItemId) +
                    "</xmp>" +
                    "</div>"
                    , "utf-8").replaceAll("\\+", "%20");
            sourceWebView.loadData(formattedHtml, "text/html", "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void onSearch(String searchText) {
        currentSearchText = searchText;
        sourceWebView.findAllAsync(searchText);
    }

    public void searchPrevClick(View view) {
        sourceWebView.findNext(false);
    }

    public void searchNextClick(View view) {
        sourceWebView.findNext(true);
    }

    private class FindCountListener implements WebView.FindListener {
        @Override
        public void onFindResultReceived(int activeMatchOrdinal, int numberOfMatches, boolean isDoneCounting) {
            if (!isDoneCounting) {
                return;
            }
            if (StringUtils.isNotBlank(currentSearchText)) {
                int foundPosition = numberOfMatches > 0 ? ++activeMatchOrdinal : 0;
                countTextView.setText(foundPosition + "/" + numberOfMatches);
            } else {
                countTextView.setText("");
            }
        }
    }
}
