package org.lds.ldssa.ux.about

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_app_info.*
import me.eugeniomarletti.extras.ActivityCompanion
import me.eugeniomarletti.extras.intent.IntentExtra
import me.eugeniomarletti.extras.intent.base.Long
import org.json.JSONException
import org.json.JSONObject
import org.lds.ldssa.R
import org.lds.ldssa.inject.Injector
import org.lds.ldssa.model.database.userdata.annotation.AnnotationManager
import org.lds.ldssa.model.database.userdata.link.LinkManager
import org.lds.ldssa.model.database.userdata.notebook.NotebookManager
import org.lds.ldssa.model.database.userdata.notebookannotation.NotebookAnnotationManager
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class AnnotationInfoActivity : AppCompatActivity() {
    @Inject
    lateinit var annotationManager: AnnotationManager
    @Inject
    lateinit var linkManager: LinkManager
    @Inject
    lateinit var notebookAnnotationManager: NotebookAnnotationManager
    @Inject
    lateinit var notebookManager: NotebookManager
    @Inject
    lateinit var gson: Gson

    init {
        Injector.get().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)

        try {
            with (IntentOptions) {
                showAnnotationInfo(intent.annotationId)
            }
        } catch (e: Exception) {
            Timber.e(e, "Failed to show Annotation Info")
        }
    }

    @Throws(IOException::class, JSONException::class)
    private fun showAnnotationInfo(annotationId: Long) {
        val sb = StringBuilder()
        sb.append("=== Annotation Info ===\n")

        val annotation = annotationManager.findFullAnnotationByRowId(annotationId)

        if (annotation == null) {
            sb.append("Could not find annotation id: ").append(annotationId)
            infoTextView.text = sb.toString()
            return
        }

        val dtoAnnotation = annotation.toDtoAnnotation(linkManager, notebookManager, notebookAnnotationManager)

        val jsonText = gson.toJson(dtoAnnotation)

        // make the json pretty
        val jsonObj = JSONObject(jsonText)
        sb.append(jsonObj.toString(2))

        infoTextView.text = sb.toString()
    }

    companion object : ActivityCompanion<IntentOptions>(IntentOptions, AnnotationInfoActivity::class)

    object IntentOptions {
        var Intent.annotationId by IntentExtra.Long(defaultValue = 0L)
    }
}
