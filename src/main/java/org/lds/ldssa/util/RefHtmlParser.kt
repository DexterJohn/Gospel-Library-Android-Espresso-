package org.lds.ldssa.util

import org.jsoup.Jsoup
import java.util.ArrayList

class RefHtmlParser(html: String) {

    var htmlTitle = ""
    val scriptureRefList = ArrayList<ScriptureRef>()

    init {
        var formattedHtml = html
        // because all <a href/> elements separate with a ; and end with a . then just remove
        formattedHtml = formattedHtml.replace("</a>.", "</a>")
        formattedHtml = formattedHtml.replace("</a>;", "</a>")

        val document = Jsoup.parse(formattedHtml)

        // get all of the scripture refs, then remove them
        document.getElementsByClass("scripture-ref").forEach { scriptureRefElement ->
            scriptureRefList.add(ScriptureRef(scriptureRefElement.text(), scriptureRefElement.attr("href")))
            scriptureRefElement.remove()
        }

        // get the remaining html title
        htmlTitle = document.getElementsByTag("p")[0].html()
    }

    inner class ScriptureRef(val title: String, val uri: String)
}
