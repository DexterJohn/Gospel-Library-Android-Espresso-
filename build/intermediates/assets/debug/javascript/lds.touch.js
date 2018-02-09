window.glContentInterface = window.glContentInterface || undefined;

// Namespace
LDSContentTouch = function (element, handler) {
    this.element = element;
    this.handler = handler;

    element.addEventListener('touchstart', this, false);

    if (LDS.util.mouseSupport() == true) {
        element.addEventListener('mousedown', this, false);
    }
};

LDSContentTouch.prototype.handleEvent = function (event) {
    //this.consoleLog("handleEvent type: [" + event.type + "]  obj: [" + event + "]");

    switch (event.type) {
        case 'touchstart':
        case 'mousedown':
            this.onTouchStart(event);
            break;
        case 'touchmove':
            this.onTouchMove(event);
            break;
        case 'touchend':
        case 'mouseup':
            this.onTouchEnd(event);
            break;
    }
};

LDSContentTouch.prototype.onTouchStart = function (event) {
    var element;
    var eventX;
    var eventY;

    switch (event.type) {
        case 'touchstart':
            this.element.addEventListener('touchend', this, false);
            element = event.changedTouches[0].target;
            var touch = event.touches[0];
            eventX = touch.clientX;
            eventY = touch.clientY;
            break;
        case 'mousedown':
            this.element.addEventListener('mouseup', this, false);
            element = event.target;
            eventX = event.clientX;
            eventY = event.clientY;
            break;
    }

    this.consoleLog("onTouchStart [" + element + "]");

    document.body.addEventListener('touchmove', this, false);

    this.startX = eventX;
    this.currentX = eventX;
    this.startY = eventY;
    this.currentY = eventY;

    this.touchHandledByLongPress = false;

    var that = this;

    var targetId = element.getAttribute("id");

    if (element instanceof HTMLAnchorElement) {
        this.consoleLog("href");

        // prevent the webview browser from processing links
        function handler(event) {
            event.stopPropagation();
            return false;
        }

        this.element.onclick = handler;

        // create the longpress timer
        this.longTapTimer = setTimeout(function () {
            that.processContentLongClick(eventX, eventY, that.currentX, that.currentY, 'link', element.getAttribute("href"), element.text);
        }, 500);
    } else if (targetId !== null && targetId.startsWith('img')) {
        this.consoleLog("img");
        // prevent the webview browser from processing links
        function handler(event) {
            event.stopPropagation();
            return false;
        }

        this.element.onclick = handler;
    } else {
        this.consoleLog("other element");
        this.longTapTimer = setTimeout(function () {
            that.processContentLongClick(eventX, eventY, that.currentX, that.currentY, 'other');
        }, 500);
    }
};

LDSContentTouch.prototype.onTouchMove = function (event) {
    // update the current x/y
    this.currentX = event.touches[0].clientX;
    this.currentY = event.touches[0].clientY;
};

LDSContentTouch.prototype.onTouchEnd = function (event) {
    // manually process tappable links instead of letting the webview handle them
    // This is now being handled by the ContentWebViewClient instead of through javascript. Do not handle it here using methods like the one below.
    // The below method causes scrolling issues on some 5.0 and less devices (event.preventDefault() should only be used on Android 6.0+ devices)
    if (LDS.util.mouseSupport()) {
        event.preventDefault();
    }

    var targetElement = event.target;
    var targetParentElement = targetElement.parentElement;
    this.consoleLog("onTouchEnd [" + targetElement + "]");

    if (this.longTapTimer !== null) {
        clearTimeout(this.longTapTimer);
        this.longTapTimer = null;
    }

    if (Math.abs(this.currentX - this.startX) >= 4 || Math.abs(this.currentY - this.startY) >= 4) {
        return;
    }

    // if onTouchStart long-press handled this event, then just return
    if (this.touchHandledByLongPress) {
        return;
    }

    if (targetElement !== null) {
        var targetClassname = targetElement.getAttribute("class");
        var targetId = targetElement.getAttribute("id");
        var footnoteElement = LDS.main.getRefSpanElement(targetElement);

//        this.consoleLog("is ref?? " + (footnoteElement));
//        this.consoleLog("is href?? " + (targetElement instanceof HTMLAnchorElement));
//        this.consoleLog("is div?? " + (targetElement instanceof HTMLDivElement));
//        this.consoleLog("is video play?? " + (targetClassname == 'video_button_play'));
//        this.consoleLog("is video download?? " + (targetClassname == 'video_button_download'));
//        this.consoleLog("is img?? " + (targetElement.tagName.toUpperCase() == "IMG")));

        if (footnoteElement) {
            // REF
            var ref = footnoteElement.getAttribute("data-ref");

            var refText = "";
            for (var i = 0; i < footnoteElement.childNodes.length; i++) {
                var curNode = footnoteElement.childNodes[i];
                if (curNode.nodeType === Node.TEXT_NODE) {
                    refText = refText + ' ' + curNode.nodeValue;
                }
            }

            var touchData = {
                'touchSource': 'ref',
                'x': this.currentX,
                'y': this.currentY,
                'ids': [ref],
                'uri': '',
                'text': refText
            };

            this.contentSingleClick(JSON.stringify(touchData));
            // reset
            this.ref = null;
        } else if (targetElement instanceof HTMLAnchorElement) {
            // LINK
            var touchData = {
                'touchSource': 'link',
                'x': this.currentX,
                'y': this.currentY,
                'ids': [],
                'uri': targetElement.getAttribute("href"),
                'text': targetElement.textContent
            };

            this.contentSingleClick(JSON.stringify(touchData));
        } else if (targetParentElement != null && targetParentElement instanceof HTMLAnchorElement) {
            // LINK - some hrefs are the parent to the targetElement (example: some email links in content)
            var touchData = {
                'touchSource': 'link',
                'x': this.currentX,
                'y': this.currentY,
                'ids': [],
                'uri': targetParentElement.getAttribute("href"),
                'text': targetParentElement.textContent
            };

            this.contentSingleClick(JSON.stringify(touchData));
        } else if (targetClassname == 'video_button_play' || (targetElement != null && targetElement instanceof HTMLVideoElement)) {
            var touchData = {
                'touchSource': 'video',
                'x': this.currentX,
                'y': this.currentY,
                'ids': [targetElement.getAttribute("data-video-id")],
                'uri': '',
                'text': ''
            };

            this.contentSingleClick(JSON.stringify(touchData));
        } else if (targetClassname == 'video_button_download') {
            LDS.util.showAllAttrs("Video Download Button", targetElement);

            var touchData = {
                'touchSource': 'video-download',
                'x': this.currentX,
                'y': this.currentY,
                'ids': [targetElement.getAttribute("data-video-id")],
                'uri': '',
                'text': ''
            };

            this.contentSingleClick(JSON.stringify(touchData));
        } else if (targetElement.tagName.toUpperCase() == "IMG") {
            var touchData = {
                'touchSource': 'image',
                'x': this.currentX,
                'y': this.currentY,
                'ids': [targetId],
                'uri': targetElement.getAttribute("src"),
                'text': ''
            };

            this.contentSingleClick(JSON.stringify(touchData));
        } else if (targetElement instanceof HTMLDivElement) {
            // STICKY
            this.consoleLog("sticky touched [" + targetElement + "]");

            // determine if div is sticky element
            var stickyElement = null;
            if (targetElement.hasAttribute("sticky")) {
                stickyElement = targetElement;
            } else if (targetElement.parentElement.hasAttribute("sticky")) {
                stickyElement = targetElement.parentElement;
            }

            // div IS sticky element
            if (stickyElement !== null) {
                var touchData = {
                    'touchSource': 'sticky',
                    'x': this.currentX,
                    'y': this.currentY,
                    'ids': LDS.annotation.getStickyIds(stickyElement), // add all stickies... (including overlapping)
                    'uri': '',
                    'text': ''
                };

                this.contentSingleClick(JSON.stringify(touchData));
            } else {
                // HTMLDivElement is unhandled... treat as "other"
                this.contentSingleClickOther()
            }
        } else {
            // unknown... treat as "other"
            this.contentSingleClickOther()
        }
    } else {
        this.handler(event);
    }
};

LDSContentTouch.prototype.contentSingleClickOther = function () {
    var touchData = {
        'touchSource': 'other',
        'x': this.currentX,
        'y': this.currentY,
        'ids': [],
        'uri': '',
        'text': ''
    };

    this.contentSingleClick(JSON.stringify(touchData));
}

LDSContentTouch.prototype.processContentLongClick = function (eventX, eventY, currentX, currentY, touchSource, uri, text) {
    if (this.touchInSamePosition(eventX, eventY, this.currentX, this.currentY)) {
        var touchData = {
            'touchSource': touchSource,
            'x': eventX,
            'y': eventY,
            'ids': [],
            'uri': uri,
            'text': text
        };

        this.touchHandledByLongPress = true;
        this.contentLongClick(JSON.stringify(touchData));
    }
}

LDSContentTouch.prototype.touchInSamePosition = function (eventX, eventY, currentX, currentY) {
    var threshold = 8; // // be sure to ALSO change ContentWebVeiw.WEB_TAP_POSITION_THRESHOLD
    var deltaX = Math.abs(eventX - currentX);
    var deltaY = Math.abs(eventY - currentY);

    return deltaX <= threshold && deltaY <= threshold;
}

LDSContentTouch.prototype.consoleLog = function (msg) {
    LDS.util.consoleLog("=== LDSContentTouch: " + msg);
}

LDSContentTouch.prototype.contentSingleClick = function (json) {
    window.glContentInterface.jsContentSingleClick(json);
}

LDSContentTouch.prototype.contentLongClick = function (json) {
    window.glContentInterface.jsContentLongClick(json);
}

new LDSContentTouch(document.body, function (event) {
});

// Notify whenever the text selection changes
document.addEventListener("selectionchange", function () {
    if (document.getSelection().rangeCount > 0) {
        //this.consoleLog('gl://selection/changed');
//        document.location.href = 'gl://selection/changed';
    }
}, false);