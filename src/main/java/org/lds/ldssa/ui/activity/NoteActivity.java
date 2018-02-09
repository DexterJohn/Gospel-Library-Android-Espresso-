package org.lds.ldssa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;
import org.lds.ldssa.R;
import org.lds.ldssa.analytics.Analytics;
import org.lds.ldssa.inject.Injector;
import org.lds.ldssa.intent.InternalIntents;
import org.lds.ldssa.model.database.userdata.annotation.Annotation;
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager;
import org.lds.ldssa.model.database.userdata.note.Note;
import org.lds.ldssa.model.database.userdata.note.NoteManager;
import org.lds.ldssa.model.prefs.Prefs;
import org.lds.ldssa.ui.widget.MarkdownControls;
import org.lds.ldssa.util.annotations.AnnotationListUtil;
import org.lds.ldssa.util.annotations.NoteUtil;
import org.lds.mobile.markdown.widget.MarkdownEditText;
import org.lds.mobile.ui.util.LdsDrawableUtil;
import org.lds.mobile.util.LdsKeyboardUtil;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Maybe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import pocketknife.BindExtra;
import pocketknife.SaveState;

public class NoteActivity extends BaseActivity {
    public static final String EXTRA_ANNOTATION_ID = "EXTRA_ANNOTATION_ID";
    public static final String EXTRA_NOTEBOOK_ID = "EXTRA_NOTEBOOK_ID";
    public static final String EXTRA_ADD_JOURNAL = "EXTRA_ADD_JOURNAL";

    @Inject
    NoteManager noteManager;
    @Inject
    AnnotationListUtil annotationListUtil;
    @Inject
    NoteUtil noteUtil;
    @Inject
    AnnotationManager annotationManager;
    @Inject
    LdsKeyboardUtil keyboardUtil;
    @Inject
    InternalIntents internalIntents;
    @Inject
    Analytics analytics;
    @Inject
    Prefs prefs;

    @BindView(R.id.noteTitleEditText)
    EditText noteTitleEditText;
    @BindView(R.id.markdownEditText)
    MarkdownEditText markdownEditText;
    @BindView(R.id.markdownControls)
    MarkdownControls markdownControls;

    @SaveState
    @BindExtra(EXTRA_ANNOTATION_ID)
    long annotationId;
    @BindExtra(EXTRA_NOTEBOOK_ID)
    long notebookId;
    @SaveState
    @BindExtra(EXTRA_ADD_JOURNAL)
    boolean addJournal;

    @Nullable
    private Note note;

    private MenuItem linkMenuItem;
    private AtomicBoolean noteLoading = new AtomicBoolean(false); // Used to determine if a note needs to be re-loaded when resuming the activity

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Injector.INSTANCE.get().inject(this);
        ButterKnife.bind(this);

        // enforce max length of content
        noteTitleEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Note.TITLE_MAX_LENGTH)});
        markdownEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(Note.CONTENT_MAX_LENGTH)});

        //Updates the home icon to be a check mark
        if (getMainToolbar() != null) {
            getMainToolbar().setNavigationIcon(LdsDrawableUtil.INSTANCE.tintDrawableForTheme(this, R.drawable.ic_lds_action_done_24dp, R.attr.colorAccent));
        }

        setTitle("");
        loadNote();

        markdownControls.setMarkdownEditText(markdownEditText);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (markdownEditText.getText().length() == 0) {
            markdownEditText.requestFocus();
        }
    }

    @Override
    protected void onPause() {
        saveNote(false);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // If the load did not complete before onPause finished, then we need to reload the note here
        if (noteLoading.get()) {
            loadNote();
        }
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_note;
    }

    @NonNull
    @Override
    protected String getAnalyticsScreenName() {
        return Analytics.Screen.NOTE_EDIT;
    }

    @Override
    public void onBackPressed() {
        saveNote(true);
        if (annotationManager.trashAnnotationIfNoSavableContent(annotationId)) {
            logAnalytics(Analytics.ChangeType.DELETE);
        }
        finishWithResult(RESULT_OK);
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_note, menu);
        LdsDrawableUtil.INSTANCE.tintAllMenuIconsForTheme(this, menu, R.attr.themeStyleColorToolbarActionModeIcon);

        linkMenuItem = menu.findItem(R.id.note_menu_link);

        if (prefs.isDeveloperModeEnabled()) {
            MenuItem annotationInfoMenuItem = menu.findItem(R.id.note_menu_show_annotation_info);
            if (annotationInfoMenuItem != null) {
                annotationInfoMenuItem.setVisible(true);
            }
        }

        updateLinkButtonVisibility();

        return true;
    }

    private void updateLinkButtonVisibility() {
        // Links cannot be associated to an annotation unless there is a docId (limitation of the Annotation Sync Service)
        boolean visible = false;
        if (annotationId > 0) {
            visible = annotationManager.findIfDocIdExists(annotationId);
        }
        linkMenuItem.setVisible(visible);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.note_menu_add_to_notebook:
                if (saveNote(false)) {
                    internalIntents.showNotebookSelection(this, getScreenId(), annotationId);
                }
                return true;
            case R.id.note_menu_tag:
                if (saveNote(false)) {
                    internalIntents.showTagSelection(this, getScreenId(), annotationId);
                }
                return true;
            case R.id.note_menu_link:
                if (saveNote(false)) {
                    internalIntents.showLinks(this, getScreenId(), annotationId);
                }
                return true;
            case R.id.note_menu_show_annotation_info:
                internalIntents.showAnnotationInfo(this, annotationId);
                return true;
            default:
                //Do nothing
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean canShowAccountSignIn() {
        return false;
    }

    private void loadNote() {
        noteLoading.set(true);

        // Try to place the cursor back to where it was (if the note content changed this may not be exactly where it was before, but should usually be close)
        int position = markdownEditText.getSelectionEnd();

        if (annotationId <= 0) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
            return;
        }

        Maybe<Note> observable = noteManager.findByAnnotationIdRx(annotationId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        addDisposable(observable.subscribe( data-> {
                note = data;
                noteLoading.set(false);
                displayNote(note);
                markdownEditText.setSelection(position);
        }));
    }

    private void displayNote(@Nullable Note note) {
        if (note != null) {
            noteTitleEditText.setText(note.getTitle());
            noteUtil.convertLegacyNote(note);
            markdownEditText.setMarkdown(note.getContent());
        }

        if (markdownEditText.getText().length() != 0) {
            keyboardUtil.hideKeyboard(this);
        }
    }

    private boolean saveNote(boolean activityFinishing) {
        String title = noteTitleEditText.getText().toString();
        String content = markdownEditText.getMarkdown();

        final boolean emptyContent = StringUtils.isEmpty(content);
        final boolean emptyTitle = StringUtils.isEmpty(title);

        boolean success = true;

        if (activityFinishing) {
            if (addJournal && (!emptyTitle || !emptyContent)) {
                // Create a new journal annotation if the note is not empty and an annotation does not exist.
                success = addJournalAnnotation(title, content);
                logAnalytics(Analytics.ChangeType.CREATE);
            } else if (note == null && (!emptyTitle || !emptyContent)) {
                // If a note does not exist and the note is not empty then add the note to the existing annotation.
                success = noteUtil.add(annotationId, title, content);
                logAnalytics(Analytics.ChangeType.CREATE);
            } else if (note != null) {
                // If the note exists then update it.
                success = noteUtil.update(note, title, content);
                logAnalytics(Analytics.ChangeType.UPDATE);
            }
        } else {
            if (addJournal) {
                // Create a new journal annotation if one does not exist.
                success = addJournalAnnotation(title, content);
                if (success) {
                    addJournal = false;
                }
            } else if (note == null && (!emptyTitle || !emptyContent)) {
                // If a note does not exist and the note is not empty then add the note to the existing annotation.
                success = noteUtil.add(annotationId, title, content);
                logAnalytics(Analytics.ChangeType.CREATE);
            } else if (note != null) {
                // If the note exists then update it.
                success = noteUtil.update(note, title, content);
                logAnalytics(Analytics.ChangeType.UPDATE);
            }
        }

        // always reload then note because the _id's may be set and any following code may need to know of the saved changes (onPause())
        loadNote();

        return success;
    }

    private boolean addJournalAnnotation(String title, String content) {
        Annotation annotation = noteUtil.createJournalAnnotation(notebookId);
        annotationId = annotation.getId();
        updateLinkButtonVisibility();
        addJournal = false;
        return noteUtil.add(annotation.getId(), title, content);
    }

    private void finishWithResult(int resultCode) {
        Intent resultIntent = new Intent();

        resultIntent.putExtra(EXTRA_ANNOTATION_ID, annotationId);
        setResult(resultCode, resultIntent);

        finish();
    }

    private void logAnalytics(Analytics.ChangeType changeType) {
        analyticsUtil.logContentAnnotated(annotationId, Analytics.AnnotationType.NOTE, changeType);
    }

}