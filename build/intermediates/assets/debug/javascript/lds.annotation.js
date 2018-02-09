// Namespace
var LDS = LDS || {};

window.glContentInterface = window.glContentInterface || undefined;
document.caretRangeFromPoint = document.caretRangeFromPoint || undefined;

// annotation module
LDS.annotation = function () {
    var self = {};

    var testMode = false; // true == allow testing on a computer
    var touchEndEventName = testMode ? "onclick" : "onTouchEnd";

    var adjustHandleY = 0;
    var adjustHandleX = 0;

    // boolean flags
    var isInSelectMode = false;
    var isSelecting = false;
    var didTouchLeftHandle = false;
    var didTouchRightHandle = false;
    var touchDownDidFire = false;

    var touchDownPoint = null;

    var selectionRangeStart = null;
    var selectionRangeEnd = null;
    var selectedAnnotation = null;

    /* ===================================
     * Touch Text
     * =================================== */
    self.onTouchDown = function (x, y) {
        consoleLog("onTouchDown");
        touchDownDidFire = true;
        if (isSelecting) {
            return true;
        }

        saveTouchDownPoint(x, y);

        return true;
    };

    self.onTouchMove = function (x, y) {
        if (!isSelecting) return true;

        var range1 = createRangeAtPoint(touchDownPoint);
        if (range1 == null) {
            if (didTouchRightHandle) {
                range1 = selectionRangeStart.cloneRange();
            } else {
                range1 = selectionRangeEnd.cloneRange();
            }
        } else if (!didTouchRightHandle && !didTouchLeftHandle) {
            range1.expand("word");
        }

        if (range1 == null) {
            return true;
        }

        var rect1 = range1.getClientRects()[0];

        x += adjustHandleX;
        y -= adjustHandleY;

        var point = {'x': x, 'y': y};
        var range2 = createRangeAtPoint(point);
        range2.expand("word");

        if (y < rect1.top || (y < rect1.bottom && x < rect1.left)) {
            selectionRangeStart = range2.cloneRange();
            selectionRangeEnd = range1.cloneRange();
            range1.setStart(range2.startContainer, range2.startOffset);
        } else {
            selectionRangeStart = range1.cloneRange();
            selectionRangeEnd = range2.cloneRange();
            range1.setEnd(range2.endContainer, range2.endOffset);
        }

        var sel = window.getSelection();
        //r1.expand("word");
        sel.removeAllRanges();
        sel.addRange(range1);
        range2.detach();

        //consoleLog("WebKit onMove: (" + x + ", " + y + ")");
        return true;
    };

    self.onTouchUp = function () {
        //consoleLog("onTouchUp: " + document.getSelection());
        touchDownDidFire = false;
        self.showMarkers();
        if (!isSelecting) return true;

        touchDownPoint = null;
        isSelecting = false;
        didTouchLeftHandle = false;
        didTouchRightHandle = false;
        adjustHandleY = 0;
        adjustHandleX = 0;

        var sel = window.getSelection();
        if (sel != null && !sel.isCollapsed) {
            //snapSelectionToWord(sel, ver);
            //removeTrailingSpace(sel);
            var range = sel.getRangeAt(0);
            if (range != null) {
                deselectWhiteSpace(range);
                showHighlightForRange(range, selectedAnnotation, true);
                selectedAnnotation = null;
                sel.removeAllRanges();
                try {
                    range.detach();
                } catch (err) {
                }
            }
        }

        return true;
    };

    /* ===================================
     * Annotation Highlight
     * =================================== */
    self.showHighlight = function (annotationJson, selectAnnotation) {
        var annotationObj = JSON.parse(annotationJson, null);

        // clear any existing selections
        var sel = window.getSelection();
        sel.removeAllRanges();

        var highlights = annotationObj.highlights;
        if (highlights != null && highlights.length > 0) {
            // get highlight range
            var highlightRange = LDS.selection.getRangeForWordOffsets(highlights);
            //consoleLog("textInHighlightRange: '" + highlightRange + "'");

            // highlight text
            showHighlightForRange(highlightRange, annotationObj, selectAnnotation);
        }
    };

    function showHighlightForRange(range, annotationObj, selectAnnotation) {
        try {
            if (range != null) {
                // be sure the selection is wrapping full words
                var startRange = range.cloneRange();
                startRange.collapse(true);
                startRange.expand("word");
                selectionRangeStart = startRange.cloneRange();
                startRange.detach();

                var endRange = range.cloneRange();
                endRange.collapse(false);
                endRange.expand("word");
                selectionRangeEnd = endRange.cloneRange();
                endRange.detach();

                // remove any existing divs for this annotation
                self.removeDivsForAnnotationId(annotationObj.id);

                // get offsets for range
                var wordOffsets = LDS.selection.getWordOffsetsFromRange(range);

                // add color name, color value and style to word offsets
                var highlightDataArray = [];
                if (wordOffsets != null) {
                    for (var i = 0; i < wordOffsets.length; i++) {
                        var highlightInfo = wordOffsets[i];
                        highlightInfo.colorName = annotationObj.colorName;
                        highlightInfo.style = annotationObj.style;
                        highlightDataArray.push(highlightInfo);
                    }
                }


                var clientRects = LDS.selection.getRectsForRange(range);
                var boundingRect = document.documentElement.getBoundingClientRect();
                var xOffset = boundingRect.left; // if we're swiping between chapters
                var entireLineRect = null;

                var yOffset = window.pageYOffset;
                var keepers = []; // this is the array of coalesced rects (1 per line) of text
                for (var j = 0; j < clientRects.length; j++) {
                    var rect = clientRects[j];
                    var left = rect.left - xOffset;
                    var top = rect.top + yOffset;
                    var width = rect.width;
                    var height = rect.height;

                    // adjust the rect height a little bit to make
                    // for nicer-size highlight boxes
                    var tAdjust = height * 0.15;
                    var hAdjust = height * 0.20;
                    top += tAdjust;
                    height -= hAdjust;

                    var currentRect = new HighlightRect(left, top, width, height);

                    // check for line overlap. if there is overlap, adjust the
                    // existing rect to swallow the new one.
                    var isNewLine = true;
                    if (entireLineRect != null && currentRect != null) {
                        var rectIsOffset = entireLineRect.isOnSameLineAsRect(currentRect);
                        var rectIsContained = !rectIsOffset && entireLineRect.containsOrIsContainedByRect(currentRect);

                        if (rectIsOffset || rectIsContained) {
                            isNewLine = false;

                            // set the left value of entireLineRect the leftmost "left" value of the two rects
                            entireLineRect.left = Math.min(currentRect.left, entireLineRect.left);
                            // set the right value of entireLineRect the rightmost "right" value of the two rects
                            entireLineRect.right = Math.max(currentRect.right, entireLineRect.right);

                            if (rectIsOffset) {
                                // set the top value of entireLineRect the lowest (visually on the page) "top" value of the two rects
                                entireLineRect.top = Math.max(currentRect.top, entireLineRect.top);
                                // set the bottom value of entireLineRect the lowest (visually on the page) "bottom" value of the two rects
                                entireLineRect.bottom = Math.max(currentRect.bottom, entireLineRect.bottom);
                            } else {
                                // set the top value of entireLineRect the highest (visually on the page) "top" value of the two rects
                                entireLineRect.top = Math.min(currentRect.top, entireLineRect.top);
                                // set the bottom value of entireLineRect the lowest (visually on the page) "bottom" value of the two rects
                                entireLineRect.bottom = Math.max(currentRect.bottom, entireLineRect.bottom);
                            }

                            // re-calculate width and height with our new values
                            entireLineRect.width = entireLineRect.right - entireLineRect.left;
                            entireLineRect.height = entireLineRect.bottom - entireLineRect.top;

                            //consoleLog("UPDATED: " + entireLineRect.left + ", " + entireLineRect.top + ", " + entireLineRect.width + ", " + entireLineRect.height);
                        }
                    }

                    if (isNewLine) {
                        // the new rect does not overlap with our entireLineRect, so save entireLineRect into the keepers array
                        pushRectIntoKeepers(entireLineRect, keepers);

                        // then create a new entireLineRect with our rect values. this should be the first rect in a new line
                        entireLineRect = currentRect;

                    }

                    // if this is our last rect, we need to push it into our keepers array
                    if (j == clientRects.length - 1) {
                        pushRectIntoKeepers(entireLineRect, keepers);
                    }
                }

                var addedRects = [];
                createHighlightDivsForRects(keepers, addedRects, annotationObj);

                if (typeof window.glContentInterface != "undefined") {
                    var selInfo = {
                        "highlights": highlightDataArray,
                        "rects": addedRects,
                        "yScrollOffset": yOffset,
                        "annotationId": annotationObj.id
                    };

                    // report back to app the current annotation text
                    var selectedText = "";
                    if (selectAnnotation) {
                        selectedText = findContentTextForRange(range);
                    }

                    //noinspection JSUnresolvedFunction
                    var selInfoString = JSON.stringify(selInfo, null);
                    window.glContentInterface.jsReportHighlightData(selInfoString, selectedText, selectAnnotation);
                }

                range.detach();
            }
            window.getSelection().removeAllRanges();
        } catch (err) {
            consoleLog("reportHighlightData Error:" + err);
        }
    }

    function createHighlightDivsForRects(keepers, addedRects, annotationObj) {
        var processed = {};

        var hasContent = annotationObj.hasContent;

        // identify the css class(s) for coloring the box or underline for the highlight
        // assume that if there is ANY style that it is a underline (only supported style... no style is just a box)
        if (annotationObj.colorName == "selection") {
            var cssClasses = "hl-box-selection";
        } else {
            if (annotationObj.style != null && annotationObj.style.length > 0) {
                var cssClasses = "hl-" + annotationObj.colorName + "-underline hl-underline";
            } else {
                // box
                var cssClasses = "hl-" + annotationObj.colorName + "-box hl-box";
            }
        }

        for (var i = 0; i < keepers.length; i++) {
            var rect = keepers[i];
            var key = rect.top + "_" + rect.left;
            if (processed[key] != 1) { //addedRects.indexOf(rect) == -1) {
                addedRects.push(rect);
                processed[key] = 1;
                //consoleLog("PROCESSED: " + key);
                var highlightDiv = document.createElement("div");
                var screenWidth = window.screen.width;
                while (rect.left > screenWidth) {
                    rect.left -= screenWidth;
                }
                while (rect.left < 0) {
                    rect.left += screenWidth;
                }
                var style = "position:absolute; top:" + rect.top + "px; left:" + rect.left + "px; width:" + rect.width + "px; height:" + rect.height + "px; z-index:-1";

                highlightDiv.setAttribute("style", style);

                var inner = document.createElement("div");
                var topAdjust = rect.height * 0.05;
                var btmAdjust = rect.height * 0.1;
                var innerStyle = "position:absolute; top:" + topAdjust + "px; width:" + rect.width + "px; height:" + (rect.height - topAdjust - btmAdjust) + "px; z-index:-1";
                inner.setAttribute("style", innerStyle);
                inner.setAttribute("class", cssClasses);
                highlightDiv.appendChild(inner);

                highlightDiv.setAttribute("annotationId", annotationObj.id);
                highlightDiv.setAttribute("hasContent", hasContent);
                if (i == 0) {
                    highlightDiv.setAttribute("firstRect", "true");
                }
                document.body.appendChild(highlightDiv);

                if (i == 0) {
                    highlightDiv.setAttribute("firstRect", "true");

                    if (hasContent) {
                        createStickyDiv(rect, annotationObj)
                    }
                }
            }
        }
    }

    function createStickyDiv(rect, annotationObj) {
        var stickyDiv = document.createElement("div");
        stickyDiv.setAttribute("style", "top: " + (rect.top - 7) + "px;");
        stickyDiv.setAttribute("annotationId", annotationObj.id);
        stickyDiv.setAttribute("hasContent", annotationObj.hasContent);
        stickyDiv.setAttribute("sticky", "true");
        stickyDiv.setAttribute("class", "sticky stickyRotate1");
        stickyDiv.setAttribute("hlTop", rect.top);
        stickyDiv.setAttribute("hlLeft", rect.left);

        var stickyShow = document.createElement("div");
        stickyShow.setAttribute("class", "stickyDisplay");
        stickyShow.setAttribute("style", "margin:0px; padding: 0px;");

        switch(annotationObj.displayType) {
            case "note":
                stickyShow.className += " stickyNote";
                break;
            case "link":
                stickyShow.className += " stickyLink";
                break;
            case "tag":
                stickyShow.className += " stickyTag";
                break;
            case "notebook":
                stickyShow.className += " stickyNotebook";
        }

        stickyDiv.appendChild(stickyShow);
        document.body.appendChild(stickyDiv);
    }

    self.clearAnnotations = function () {
        var elements = document.querySelectorAll("[annotationId]");
        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            element.parentNode.removeChild(element);
        }
    };

    /* ===================================
     * Bookmark Ribbon
     * =================================== */
    self.showBookmarkIndicator = function (annotationId, paragraphAid) {
        consoleLog("Bookmark Indicator paragraphAid: " + paragraphAid);
        self.removeDivsForAnnotationId(annotationId);
        var paragraphElement = LDS.main.findParagraphElementByAid(paragraphAid);
        var top = 96;
        if (paragraphElement != null) {
            top = paragraphElement.getBoundingClientRect().top + window.pageYOffset;
        }
        top -= 4; // adjust for top padding of tappable area
        var div = document.createElement("div");
        div.setAttribute("style", "top:" + top + "px;");
        div.setAttribute("class", "ribbon");
        div.setAttribute("annotationId", annotationId);
        div.setAttribute("paragraphAid", paragraphAid);
        var inner = document.createElement("div");
        inner.setAttribute("class", "ribbonDisplay");
        div.appendChild(inner);
        document.body.appendChild(div);
    };

    self.applyRedRibbonEffects = function (annotationId) {
        var item = document.querySelector("[annotationId=\"" + annotationId + "\"] > div.ribbonDisplay");
        if (item != null) {
            item.className += " ribbonDisplayRed";
            setTimeout(removeRedRibbonEffects, 2000);
        }
    };

    function removeRedRibbonEffects() {
        var list = document.querySelectorAll(".ribbonDisplayRed");
        for (var i = 0; i < list.length; i++) {
            var item = list[i];
            item.className = item.className.replace(/ribbonDisplayRed/g, "");
        }
    };

    /* ===================================
     * Sticky
     * =================================== */
    // called by lds.touch.js
    self.getStickyIds = function (stickyElement) {
        var annotationIds = [];
        if (stickyElement != null) {
            // check to see if there are overlapping stickies
            var overlappingStickyElements = getOverlappingStickies(stickyElement);
            var overlapping = overlappingStickyElements != null && overlappingStickyElements.length > 0;

            // send message to UI for the selected sticky or stickies
            if (overlapping) {
                // add this sticky
                overlappingStickyElements.push(stickyElement);

                // order the stickies from top to bottom (as they appear in the UI)
                overlappingStickyElements.sort(sortStickies)

                // add overlapping stickies
                for (var i = 0; i < overlappingStickyElements.length; i++) {
                    annotationIds.push(overlappingStickyElements[i].getAttribute("annotationId"));
                }
            } else {
                return [ stickyElement.getAttribute("annotationId") ]
            }
        }

        return annotationIds;
    }

    // Find any overlapping stickies with this sticky
    function getOverlappingStickies(stickyElement) {
        var elements = document.querySelectorAll(".sticky");
        var overlapping = [];
        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            if (stickyElement == element) {
                continue;
            }

            if (isOverlapping(stickyElement, element)) {
                overlapping.push(element);
            }
        }
        return overlapping;
    }

    // used to sort overlapping stickies
    function sortStickies(a, b) {
        var val = a.getAttribute("hlTop") - b.getAttribute("hlTop");
        if (val <= 2 && val >= -2) {
            //consoleLog("VAL: " + val + " " + (a.offsetLeft - b.offsetLeft));
            val = a.getAttribute("hlLeft") - b.getAttribute("hlLeft");
        }
        return val;
    }

    /* ===================================
     * Selection mode
     * =================================== */
    function createRangeAtPoint(point) {
        if (point != null) {
            if (typeof(document.caretRangeFromPoint) == "function") {
                //consoleLog("X:" + x + " Y:" + y);
                var range = document.caretRangeFromPoint(point.x, point.y);
                if (range == null) {
                    return null;
                }
                //range.expand("word");
                return range;
            }
        }

        return null;
    }

    function saveTouchDownPoint(x, y) {
        touchDownPoint = {'x': x, 'y': y};
    }

    self.enterSelectModeFromHandle = function (annotJson, x, y, rightHandle, xAdjust, yAdjust) {
        var annotObj = JSON.parse(annotJson, null);
        consoleLog("enterSelectModeFromHandle: (" + x + ", " + y + ") " + annotObj.id);

        saveTouchDownPoint(x, y - window.pageYOffset);

        adjustHandleX = xAdjust;
        adjustHandleY = yAdjust;
        isInSelectMode = true;
        isSelecting = true;
        selectedAnnotation = annotObj;
        didTouchLeftHandle = !rightHandle;
        didTouchRightHandle = rightHandle;
    };

    self.enterSelectModeFromLongPress = function (annotJson, x, y) {
        var annotObj = JSON.parse(annotJson, null);
        consoleLog("enterSelectModeFromLongPress: (" + x + ", " + y + ") " + annotObj.id);
        saveTouchDownPoint(x, y);

        if (!touchDownDidFire) {
            if (typeof window.glContentInterface != "undefined") {
                // todo... is this still really an issue??
                // As of 02/24/2017 this scenario had occurred 194k times for 114k users over the course of the previous 30 days
                // A multitude of devices running every android version from 4 to 7 reported the issue with no correlating information.
                // Reporting of the issue to Fabric was removed at that time
                // noinspection JSUnresolvedFunction
                window.glContentInterface.jsReportSelectionProblem();
            }
            return;
        }
        var range = createRangeAtPoint(touchDownPoint);

        if (range == null) {
            consoleLog("Unable to create range for selection!");
            return;
        }

        // expand to the word boundaries
        range.expand("word");

        // validate the range placement contains the touch
        if (!rangeContainsPoint(range, touchDownPoint)) {
            range.detach();
            var adjustment = 12;
            //if (!adjusted) { // first move the touch point down and try again
            consoleLog("Adjusting invalid range down (point.y+" + adjustment + ")...");
            self.enterSelectModeFromLongPress(annotJson, x, y + adjustment, "down");
            //} else if (adjusted === "down") { // next move the touch point up and try again
            //    consoleLog("Adjusting invalid range up (point.y-" + adjustment + ")...");
            //    self.enterSelectModeFromLongPress(annotJsonText, x, y - adjustment * 2, "up");
            //} else if (adjusted === "up") { // next move the touch point left and try again
            //    consoleLog("Adjusting invalid range left (point.x-" + adjustment + ")...");
            //    self.enterSelectModeFromLongPress(annotJsonText, x - adjustment, y + adjustment, "left");
            //} else { // give up
            //    consoleLog("Invalid touch point to create a selection -- aborting!");
            //}
            return;
        }

        // validate the range contents contain text
        var rangeText = range.toString().trim();
        if (rangeText === "") {
            try {
                consoleLog("Adjusting empty range left on character...");
                range.setStart(range.startContainer, range.startOffset - 1);
                range.expand("word");
            } catch (err) {
                consoleLog("Unable to adjust empty range -- aborting!");
                range.detach();
                return;
            }
            rangeText = range.toString().trim();
            if (rangeText === "") {
                consoleLog("Range is still empty after adjustment -- aborting!");
                range.detach();
                return;
            }
        }

        deselectWhiteSpace(range);

        isInSelectMode = true;
        isSelecting = true;
        selectedAnnotation = annotObj;

        var sel = window.getSelection();
        sel.removeAllRanges();
        //sel.addRange(r1);
        selectionRangeStart = range.cloneRange();
        selectionRangeEnd = range.cloneRange();
        showHighlightForRange(range, annotObj, true);

    };

    function deselectWhiteSpace(range) {
        if (range != null) {
            var rangeText = range.toString();
            if (rangeText.length > 1) {
                var whitespace = /\s+|[\u002D\u058A\u05BE\u1400\u1806\u2010-\u2015\u2053\u207B\u208B\u2212\u2E17\u2E1A\u2E3A-\u301C\u3030\u30A0\uFE31\uFE32\uFE58\uFE63\uFF0D]/g;
                var firstChar = rangeText.substr(0, 1);
                if (firstChar.match(whitespace)) {
                    try {
                        var startOffset = range.startOffset + 1
                        if (startOffset > range.startContainer.length) {
                            // The selected text starts at the end of a verse/paragraph. This is an invalid position. The best we can do is select the start of the node where the end container is located.
                            range.setStart(range.endContainer, 0)
                            deselectWhiteSpace(range)
                        } else {
                            // The selected text starts within a verse/paragraph.
                            consoleLog("Removing whitespace from the beginning of the range...");
                            range.setStart(range.startContainer, startOffset);
                        }
                    } catch (err) {
                        consoleLog("Unable to adjust range start boundary: " + err);
                    }
                }
                var lastChar = rangeText.substr(rangeText.length - 1, 1);
                if (lastChar.match(whitespace)) {
                    try {
                        consoleLog("Removing whitespace from the end of the range...");
                        range.setEnd(range.endContainer, range.endOffset - 1);
                    } catch (err) {
                        consoleLog("Unable to adjust range end boundary: " + err);
                    }
                }
            }
        }
    }

    function rangeContainsPoint(range, point) {
        if (range != null && point != null) {
            var rects = range.getClientRects();
            for (var i = 0; i < rects.length; i++) {
                var rect = rects[i];
                var xIsInside = rect.left < point.x && point.x < rect.right;
                var yIsInside = rect.top < point.y && point.y < rect.bottom;
                if (xIsInside && yIsInside) {
                    return true;
                }
            }
        }
        return false;
    }

    self.hideMarkers = function () {
        var elements = document.querySelectorAll(".sticky");
        for (var i = 0; i < elements.length; i++) {
            var el = elements[i];
            var clsName = el.className;
            if (clsName.indexOf("hiddenElement") < 0) {
                el.className += " hiddenElement";
            }
        }
    };

    self.showMarkers = function () {
        //consoleLog("showMarkers");
        var elements = document.querySelectorAll(".sticky, .ribbon");
        for (var i = 0; i < elements.length; i++) {
            var el = elements[i];
            var clsName = el.className;
            if (clsName.indexOf("hiddenElement") != -1) {
                el.className = clsName.replace(/hiddenElement/g, "");
            }
        }
    };

    var HighlightProperty = function (annotationId, text, color, style) {
        this.annotationId = annotationId;
        this.text = text;
        this.color = color;
        this.style = style;
    };

    var HighlightRect = function (left, top, width, height) {
        this.left = left;
        this.top = top;
        this.width = width;
        this.height = height;
        this.right = left + width;
        this.bottom = top + height;
    };

    HighlightRect.prototype.isOnSameLineAsRect = function (other) {
        if (this.top != other.top) {
            if (this.bottom > other.top && other.bottom > this.top) {
                if (this.top > other.top) { // other extends up past this
                    return this.top - other.top < other.bottom - this.top;
                } else {
                    return other.top - this.top < this.bottom - other.top;
                }
            }
        } else if (this.bottom != other.bottom) {
            if (this.bottom > other.top && other.bottom > this.top) {
                if (this.bottom < other.bottom) { // other extends down past this
                    return other.bottom - this.bottom < this.bottom - other.top;
                } else {
                    return this.bottom - other.bottom < other.bottom - this.top;
                }
            }
        }
        return false;
    }

    HighlightRect.prototype.containsOrIsContainedByRect = function (other) {
        return (other.bottom <= this.bottom && other.top >= this.top) ||
            (other.bottom >= this.bottom && other.top <= this.top);
    };


    self.requestTextForAnnotation = function (annotationJson, requestCode) {
        var annotation = JSON.parse(annotationJson, null);
        var text = findTextForAnnotation(annotation);

        if (typeof window.glContentInterface != "undefined") {
            window.glContentInterface.jsReportAnnotationText(annotation['id'], text, requestCode);
        }
    };

    self.requestAnnotationPropertiesForAnnotationList = function (annotationListJson, forNote) {
        var annotationListObj = JSON.parse(annotationListJson, null);
        var annotationList = annotationListObj.annotations;

        var annotationPropertiesList = [];
        for (var i = 0; i < annotationList.length; i++) {
            var annotation = annotationList[i];
            var text = findTextForAnnotation(annotation);
            var color = annotation.colorName;
            var style = annotation.style;

            annotationPropertiesList.push(new HighlightProperty(annotation.id, text, color, style));
        }

        window.glContentInterface.jsReportHighlightSelectionInfo(JSON.stringify(annotationPropertiesList, null), forNote);
    };

    function findTextForAnnotation(annotation) {
        var highlights = annotation.highlights;

        var text = "";
        if (highlights !== undefined) {
            try {
                var allFilter = function (node) {
                    return NodeFilter.FILTER_ACCEPT;
                };
                for (var i = 0; i < highlights.length; i++) {
                    var highlight = highlights[i];
                    var range = LDS.selection.getRangeForWordOffsets([highlight]);

                    if (text.length > 0) {
                        text += "\n";
                    }

                    text += findContentTextForRange(range);
                }
            } catch (err) {
                consoleLog("Error in requestTextForAnnotation: " + err.toString());
            }
        }

        return text;
    }

    // Used for findContentTextForRange(...)
    function allFilter(node) {
        return NodeFilter.FILTER_ACCEPT;
    };

    // Text from this call will remove any footnote <sup> and any other cleanup that needs to happen
    function findContentTextForRange(range) {
        var text = "";
        try {
            var fragment = range.cloneContents();
            var treeWalker = document.createTreeWalker(fragment, NodeFilter.SHOW_TEXT, allFilter, false);

            while (treeWalker.nextNode()) {
                var node = treeWalker.currentNode;
                var parent = node.parentElement;
                if (parent == null || parent.tagName.toLowerCase() != "sup") {
                    text += node.nodeValue;
                }
            }
        } catch (err) {
            consoleLog("Error in findContentTextForRange: " + err.toString());
        }

        return text;
    }



    self.setSelectedAnnotationIdOnHighlights = function (annotId) {
        var list = document.querySelectorAll("[annotationId=\"null\"]");
        if (list != null) {
            for (var i = 0; i < list.length; ++i) {
                var item = list[i];
                //consoleLog("Setting active ID \"" + annotId + "\" on " + item.className);
                item.setAttribute("annotationId", annotId);
            }
        }
    };

    self.removeDivsForAnnotationId = function (annotId) {
        //consoleLog("REMOVE HIGHLIGHT RECTS: " + annotId);
        var annotationElementList = document.querySelectorAll("[annotationId=\"" + annotId + "\"]");
        if (annotationElementList != null) {
            for (var i = 0; i < annotationElementList.length; ++i) {
                var annotationElement = annotationElementList[i];
                annotationElement.parentNode.removeChild(annotationElement);
            }
        }
    };

    function pushRectIntoKeepers(lineRect, keepers) {
        if (lineRect != null) {
            var spliced = false;
            var k = keepers.length;
            // Compare the top of lineRect with our last few keepers.
            // If the tops are the same, then take whichever of them is wider
            // and splice it into the keepers array
            while (k--) {
                var keeper = keepers[k];
                //consoleLog("COMPARE TOPS: " + lineRect.top + " == " + keeper.top );
                if (lineRect.top == keeper.top) {
                    //consoleLog("TOPS ARE EQUAL: " + lineRect.left + ", " + lineRect.top + ", " + lineRect.width + ", " + lineRect.height);
                    if (lineRect.width < keeper.width) {
                        lineRect = keeper;
                        //consoleLog("KEEPER IS WIDER: " + lineRect.left + ", " + lineRect.top + ", " + lineRect.width + ", " + lineRect.height);
                    }
                    keepers.splice(k, 1, lineRect);
                    spliced = true;
                    break;
                }

                // shouldn't have to go back more than 2 lines
                if (keepers.length - k > 2) {
                    break;
                }
            }

            // if we didn't need to splice the rect in to the keepers array, just push it here
            if (!spliced) {
                keepers.push(lineRect);
            }
        }
    }

    /* ===================================
     * Util
     * =================================== */
    function getStyle(element, style) {
        if (!document.getElementById) return null;

        var value = element.style[style];

        if (!value) {
            if (document.defaultView) {
                value = document.defaultView.getComputedStyle(element, "").getPropertyValue(style);
            }
        } else if (element.currentStyle) {
            value = element.currentStyle[style];
        }

        return value;
    }

    function getHighlightClassName(color, style) {
        if (style == null || style.length == 0) {
            return color;
        }
        return style;
    }


    function haltEvent(event) {
        if (event != null) {
            event.preventDefault(); // https://code.google.com/p/android/issues/detail?id=4549 (may be fixed in Android 4.1+)
            event.stopPropagation();
        }
    }

    function isOverlapping(element1, element2) {
        var pos_el1 = absolutePosition(element1);
        var pos_el2 = absolutePosition(element2);
        var top1 = pos_el1.y;
        var left1 = pos_el1.x;
        var right1 = left1 + element1.offsetWidth;
        var bottom1 = top1 + element1.offsetHeight;
        var top2 = pos_el2.y;
        var left2 = pos_el2.x;
        var right2 = left2 + element2.offsetWidth;
        var bottom2 = top2 + element2.offsetHeight;
        var _getSign = function (v) {
            if (v > 0) {
                return "+";
            } else if (v < 0) {
                return "-";
            } else {
                return 0;
            }
        };
        return (_getSign(top1 - bottom2) != _getSign(bottom1 - top2)) &&
            (_getSign(left1 - right2) != _getSign(right1 - left2));

    }

    function absolutePosition(element) {
        var posObj = {
            'x': element.offsetLeft,
            'y': element.offsetTop
        };
        if (element.offsetParent) {
            var temp_pos = absolutePosition(element.offsetParent);
            posObj.x += temp_pos.x;
            posObj.y += temp_pos.y;
        }
        return posObj;
    }


    self.requestAllParagraphAidPositions = function () {
        var elements = LDS.main.findAllParagraphAidElements();
        var paragraphAidPositions = [];
        for (var i = 0; i < elements.length; i++) {
            var element = elements[i];
            var paragraphAidPosition = {
                'paragraphAid': LDS.main.getElementAid(element),
                'positionY': element.offsetTop
            };
            paragraphAidPositions.push(paragraphAidPosition);

            //paragraphAidPosition.paragraphAid = LDS.main.getElementAid(element);
            //paragraphAidPosition.positionY = element.offsetTop;

            //paragraphAidPositions.push({'paragraphAid': LDS.main.getElementAid(element), 'positionY': element.offsetTop};)
        }

        window.glContentInterface.jsReportParagraphAidPositions(JSON.stringify(paragraphAidPositions));
    }

    self.getTopVisibleAid = function () {
        var scroll = window.pageYOffset;

        var body = document.body,
            html = document.documentElement;

        var height = Math.max(body.scrollHeight, body.offsetHeight,
            html.clientHeight, html.scrollHeight, html.offsetHeight);


        consoleLog("js scroll position: " + scroll);
        consoleLog("js height: " + height);
        consoleLog("js window height: " + window.outerHeight);
        var elements = LDS.main.findAllParagraphAidElements();
        var element;
        for (var i = 0; i < elements.length; i++) {
            element = elements[i];

            if (element.offsetTop >= scroll) {
                consoleLog("element.offsetTop: " + element.offsetTop);
                //consoleLog("data-aid: " + element.getAttribute('data-aid'));
                //
                //var returnVal = {'aid': element.getAttribute('data-aid')};
                //consoleLog("return: " + JSON.stringify(returnVal));
                //return returnVal;
                //return JSON.stringify(element);
                var paragraphAid = LDS.main.getElementAid(element)
                return paragraphAid;
                //window.glContentInterface.jsReportTopParagraphAidForBookmark(paragraphAid);
            }
        }
    };

    function consoleLog(msg) {
        LDS.util.consoleLog(msg);
    }

    return self;

}
();