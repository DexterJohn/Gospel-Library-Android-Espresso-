// Namespace
var LDS = LDS || {};

window.glContentInterface = window.glContentInterface || undefined;

// main module
LDS.main = function () {
    var self = {};

    self.getElementForPosition = function (x, y) {
        consoleLog("getElementForPosition... " + x + "," + y);

        var element = document.elementFromPoint(x, y);
        if (element !== null) {
            consoleLog(element)
        }
    };

    /* ===================================
     * Content
     * =================================== */
    self.createContentHtmlCustomizations = function () {
        LDS.media.configureVideos();
        LDS.media.configureImages();
        //self.getDocumentHeadingImage();
    };

    self.getRefSpanElement = function (element) {
        if (element.getAttribute('data-ref') !== null) {
            return element;
        }

        if (element.parentElement.getAttribute('data-ref') !== null) {
            return element.parentElement;
        }

        return null;
    };

    /* ===================================
     * Mark Text
     * =================================== */
    self.scrollToMark = function (index) {
        // remove "selected" class from any existing "mark" elements
        var allSelectedMarks = document.body.querySelectorAll('mark.selected');
        for (i = 0; i < allSelectedMarks.length; i++) {
            allSelectedMarks[i].className = "";
        }

        // find and set "selected" mark index, then scroll to that mark
        var allMarks = document.body.querySelectorAll('mark');

        if (allMarks.length >= index) {
            var selectedMark = allMarks[index];

            selectedMark.className = "selected";

            // scroll to mark
            LDS.util.scrollToElement(selectedMark);
        }
    }

    self.removeAllMarks = function () {
        consoleLog("removeAllMarks...");

        var allMarks = document.body.querySelectorAll('mark');

        for (i = 0; i < allMarks.length; i++) {
            // select element to unwrap
            var mark = allMarks[i];

            // get the element's parent node
            var parent = mark.parentNode;


            // move all children out of the element
            while (mark.firstChild) parent.insertBefore(mark.firstChild, mark);

            // remove the empty element
            parent.removeChild(mark);
        }
    };

    /* ===================================
     * Scroll and Mark Paragraphs
     * =================================== */

    /**
     * Scroll to a position.  Typically used when showing content from a bookmark or from a click in notebook
     * @param aid
     */
    self.scrollToParagraphByAid = function (aid) {
        if (aid == null || aid.length <= 0) {
            return;
        }

        LDS.util.scrollToElement(self.findParagraphElementByAid(aid));
    };

    /**
     * Scroll to a position with the option to mark(highlight) data-aids.  Typically used when showing content
     * from a link
     * @param scrollToAid scroll to position
     * @param markAids comma seperated set of aids that should be highlighted
     */
    self.scrollAndMarkParagraphsByAid = function (scrollToAid, markAids) {
        if (scrollToAid == null || scrollToAid.length <= 0) {
            return;
        }

        // mark aids
        if (markAids != null) {
            var aidList = markAids.split(",");
            for (var i = 0; i < aidList.length; i++) {
                self.markParagraphByAid(aidList[i]);
            }
        }

        // scroll to position
        LDS.util.scrollToElement(self.findParagraphElementByAid(scrollToAid));
    };

    self.markParagraphByAid = function (aid) {
        self.markParagraphByElement(self.findParagraphElementByAid(aid));
    };

    self.markParagraphByElement = function (element) {
        if (element != null) {
            element.className += " mark";
        }
    }

    self.removeParagraphMarkByAid = function (aid) {
        var element = self.findParagraphElementByAid(aid);
        if (element != null) {
            removeParagraphMarkByElement(element);
        }
    };

    self.removeAllParagraphMarks = function () {
        var list = document.querySelectorAll(".mark");
        for (var i = 0; i < list.length; i++) {
            var element = list[i];
            removeParagraphMarkByElement(element);
        }
    };

    function removeParagraphMarkByElement(element) {
        element.className = element.className.replace(/mark/g, "");
    }


    /* ===================================
     * Aid, Paragraph ID, elements
     * =================================== */

    // Returns the determined aid to the onParagraphTapped method of the webview.
    self.getAidForTapPosition = function(x, y) {
        var aid = getAidFromPosition(x, y);
        //noinspection JSUnresolvedFunction
        window.glContentInterface.jsReportAidForTapPosition(aid);
    };

    // Returns the determined aid to the onNotifyScrollPositionAid method of the webview.
    self.getAidForScrollPosition = function(x, y) {
        var aid = getAidFromPosition(x, y);
        //noinspection JSUnresolvedFunction
        window.glContentInterface.jsReportAidForScrollPosition(aid);
    };

    function getAidFromPosition(x, y) {
        var element = document.elementFromPoint(x, y);
        var aid = getAidForElement(element);
        if (aid) {
            consoleLog("Element aid: " + aid);
        }
        else {
            consoleLog("Element doesn't have aid.");
        }
        return aid;
    };

    function getAidForElement(element) {
        if (element != null) {
            //consoleLog(node.nodeName + ": " + node.textContent);
            var attrs = element.attributes;
            if (attrs != null) {
                var aid = attrs.getNamedItem("data-aid");
                if (aid != null) {
                    return aid.value;
                } else {
                    var siblings = element.parentNode.querySelectorAll("[data-aid]");
                    if (siblings == null || siblings.length == 0) {
                        return getAidForElement(element.parentNode);
                    }
                    else {
                        return null;
                    }
                }
            } else {
                return getAidForElement(element.parentNode);
            }
        }
        return null;
    }


    self.getAidForPosition = function (x, y) {
        var element = document.elementFromPoint(x, y);
        var aid = getAidForElement(element);
        if (aid) {
            consoleLog("Element aid: " + aid);
        }
        else {
            consoleLog("Element doesn't have aid.");
        }
        //noinspection JSUnresolvedFunction
        window.glContentInterface.jsReportAidForPosition(x, y, aid);
    };

    function getAidForElement(element) {
        if (element != null) {
            //consoleLog(node.nodeName + ": " + node.textContent);
            var attrs = element.attributes;
            if (attrs != null) {
                var uri = attrs.getNamedItem("data-aid");
                if (uri != null) {
                    return uri.value;
                }
                else {
                    var siblings = element.parentNode.querySelectorAll("[data-aid]");
                    if (siblings == null || siblings.length == 0) {
                        return getAidForElement(element.parentNode);
                    }
                    else {
                        return null;
                    }
                }
            }
            else {
                return getAidForElement(element.parentNode);
            }
        }
        return null;
    }

    function findParagraphElementByParagraphId(paragraphId) {
        if (paragraphId == null) {
            return null;
        }

        return document.querySelector("[id='" + paragraphId + "']");
    }

    self.findParagraphElementByAid = function (aid) {
        if (aid == null) {
            return null;
        }

        return document.querySelector("[data-aid='" + aid + "']");
    }

    self.findAllParagraphAidElements = function () {
        return document.querySelectorAll("[data-aid]");
    }

    self.getElementAid = function (element) {
        return element.getAttribute('data-aid');
    }

    /* ===================================
     * Util
     * =================================== */
    function consoleLog(msg) {
        LDS.util.consoleLog(msg);
    }

    return self;

}();