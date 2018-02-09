package org.lds.ldssa.ux.annotations.tagselection

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.InputFilter
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_tag_selection.*
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.lds.ldssa.R
import org.lds.ldssa.analytics.Analytics
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.tag.Tag
import org.lds.ldssa.model.database.userdata.tagview.TagView
import org.lds.ldssa.model.prefs.type.TagSortType
import org.lds.ldssa.ui.activity.BaseActivity
import org.lds.ldssa.ui.widget.AnnotationTagView
import org.lds.mobile.ui.setVisible
import org.lds.mobile.ui.util.LdsDrawableUtil
import org.lds.mobile.ui.widget.input.DelayTextWatcher
import javax.inject.Inject

class TagSelectionActivity : BaseActivity(), AnnotationTagView.OnTagClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(TagSelectionViewModel::class.java) }

    private var annotationId = 0L

    private val adapter = TagSelectionAdapter().apply {
        tagCheckChangedListener = { tagText, checked -> onTagCheckChanged(tagText, checked) }
        createNewClickedListener = { viewModel.addTag(getTagFilterText()) }
    }

    init {
        Injector.get().inject(this)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(IntentOptions) {
            annotationId = intent.annotationId
        }

        setupToolbar()
        setupTagEditText()
        setupTagsTable()
        setupViewModelObservers()

        viewModel.setAnnotationId(annotationId)
    }

    override fun getLayoutResourceId(): Int {
        return R.layout.activity_tag_selection
    }

    override fun getAnalyticsScreenName(): String {
        return Analytics.Screen.TAG_SELECTION
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_tag_selection, menu)
        LdsDrawableUtil.tintAllMenuIconsForTheme(this, menu, R.attr.themeStyleColorToolbarActionModeIcon)

        menu.findItem(TagSortType.getSortMenuItemId(prefs.tagSortType)).isChecked = true

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finishWithResult(RESULT_OK)
            }
            R.id.menu_item_tag_sort_alphabetical -> {
                item.isChecked = true
                viewModel.setSortType(TagSortType.ALPHABETICAL)
            }
            R.id.menu_item_tag_sort_count -> {
                item.isChecked = true
                viewModel.setSortType(TagSortType.COUNT)
            }
            R.id.menu_item_tag_sort_recent -> {
                item.isChecked = true
                viewModel.setSortType(TagSortType.MOST_RECENT)
            }
            else -> return false
        }

        return true
    }

    override fun onBackPressed() {
        finishWithResult(RESULT_OK)
    }

    override fun onDeleteTag(tagName: String, tagId: Long) {
        viewModel.removeTag(tagName)
    }

    override fun onClickTag(name: String, tagId: Long) {
        // not needed
    }

    private fun onTagCheckChanged(tagName: String, checked: Boolean) {
        if (checked) {
            viewModel.addTag(tagName)
        } else {
            viewModel.removeTag(tagName)
        }
    }

    /**
     * Listens to the tag input text to perform filtering of
     * the available tags
     */
    private fun onTagInputTextChanged(charSequence: CharSequence) {
        viewModel.setFilterText(charSequence.toString())

        if (charSequence.isNotEmpty()) {
            tagsRecyclerView.scrollToPosition(0)
        }
    }

    /**
     * Listens for the next button click on the keyboard to perform a UI
     * tag addition.
     */
    private fun onTagInputEditorAction(textView: TextView, actionId: Int): Boolean {
        if (actionId != EditorInfo.IME_ACTION_NEXT) {
            return false
        }

        //Retrieves the text (name) for the tag
        viewModel.addTag(getTagFilterText())
        textView.text = ""

        return true
    }

    private fun setupToolbar() {
        setTitle(R.string.tags)

        //Updates the home icon to be a check mark
        getMainToolbar()?.navigationIcon = LdsDrawableUtil.tintDrawableForTheme(this, R.drawable.ic_lds_action_done_24dp, R.attr.colorAccent)
    }

    private fun setupTagEditText() {
        val textWatcher = DelayTextWatcher()
        tagNameEditText.addTextChangedListener(textWatcher)
        textWatcher.asLiveData().observe(this, Observer { charSequence ->
            charSequence ?: return@Observer
            onTagInputTextChanged(charSequence)
        })

        tagNameEditText.setOnEditorActionListener { textView, i, _ -> onTagInputEditorAction(textView, i) }

        // Don't allow illegal characters in the Tag name
        val filter = InputFilter { source, start, end, _, _, _ ->
            (start until end)
                    .filter { source[it] == Tag.ILLEGAL_CHAR }
                    .forEach { return@InputFilter "" }

            return@InputFilter null // character is OK
        }

        tagNameEditText.filters = arrayOf(filter)
    }

    private fun setupTagsTable() {
        tagsRecyclerView.layoutManager = LinearLayoutManager(this)
        tagsRecyclerView.adapter = adapter
    }

    private fun setupViewModelObservers() {
        viewModel.tagsForAnnotationList.observeNotNull { data ->
            tagsFlexboxLayout.removeAllViews()
            data.forEach { tag -> addTagToUi(tag.name, tag.id) }
        }

        viewModel.tagsAvailableList.observeNotNull { data ->
            displayAvailableTags(data)
        }
    }

    private fun getTagFilterText() = tagNameEditText.text.toString()

    /**
     * Adds a new Tag widget to the list of selected tags without performing any
     * validations for duplicates, etc.
     *
     * @param tagText The text (name) for the tag
     * @param tagId   The id associated with the tag in the database
     */
    private fun addTagToUi(tagText: String, tagId: Long) {
        val tagWidget = AnnotationTagView(this, tagText)
        tagWidget.tagId = tagId
        tagWidget.setTagClickListener(this)

        tagsFlexboxLayout.addView(tagWidget, tagsFlexboxLayout.childCount - 1)

        //Clears the text
        tagNameEditText.setText("")
    }


    /**
     * Updates the list of available tags
     *
     * @param data The data with the available tags
     */
    private fun displayAvailableTags(data: List<TagView>) {
        adapter.list = data

        //Updates the view visibilities
        val hasItems = data.isNotEmpty()
        tagsRecyclerView.setVisible(hasItems, View.INVISIBLE)
        emptyStateIndicator.setVisible(!hasItems)
    }

    private fun finishWithResult(resultCode: Int) {
        if (getTagFilterText().isNotBlank()) {
            viewModel.addTag(getTagFilterText())
        }

        val resultIntent = Intent()
        with(IntentOptions) {
            resultIntent.annotationId = annotationId
        }
        setResult(resultCode, resultIntent)

        finish()
    }

    object IntentOptions {
        var Intent.annotationId by IntentExtra.Long(defaultValue = 0L)
    }
}
