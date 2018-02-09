// Namespace
var LDS = LDS || {};

// util module
LDS.util = function () {
    var self = {};

    // used for smooth scroll
    var timeoutScroll = null;

    /*
     * CSS
     */
    self.removeCssClassFromAll = function (className) {
        var list = document.querySelectorAll("." + className);
        if (list != null) {
            var expression = new RegExp(className, "g");
            for (var i = 0; i < list.length; ++i) {
                var element = list[i];
                element.className = element.className.replace(expression, "");
            }
        }
    };

    /* *****
     * Scrolling
     */
    //function currentYPosition() {
    //    // Firefox, Chrome, Opera, Safari
    //    if (pageYOffset) return pageYOffset;
    //    // Internet Explorer 6 - standards mode
    //    if (document.documentElement && document.documentElement.scrollTop)
    //        return document.documentElement.scrollTop;
    //    // Internet Explorer 6, 7 and 8
    //    if (document.body.scrollTop) return document.body.scrollTop;
    //    return 0;
    //}

    self.scrollToElement = function (element) {
        if (element == null) {
            return;
        }

        // instant scroll
        element.scrollIntoView(true);

        // offset the view (so that the text is not at the VERY top of the page)
        if (this.getAndroidVersion() >= 4.4) {
            window.scrollBy(0, -35);
        }
    }

    self.mouseSupport = function () {
        return this.getAndroidVersion() >= 6.0;
    }

    self.getAndroidVersion = function() {
        var androidVersion = 0.0;
        var ua = navigator.userAgent;
        if( ua.indexOf("Android") >= 0 ) {
          androidVersion = parseFloat(ua.slice(ua.indexOf("Android")+8));
        }

        return androidVersion;
    }

    /*  ***** UTIL ***** */
    function trim(stringToTrim) {
        return stringToTrim.replace(/^\s+|\s+$/g, '');
    }

// todo needed???
    if (typeof String.prototype.startsWith != 'function') {
        String.prototype.startsWith = function (str) {
            return this.slice(0, str.length) == str;
        };
    }

    /*
     * Debug methods
     */

    // LDS.util.showAllAttrs("Video Play", this.targetElement);
    self.showAllAttrs = function (message, element) {
        for (var i = 0, atts = element.attributes, n = atts.length, arr = []; i < n; i++){
            this.consoleLog(message + " Attribute: " + atts[i].nodeName + ", " + atts[i].nodeValue);
        }
    }

    /*
     * Logging
     */
    self.consoleLog = function (msg) {
        if (typeof window.glContentInterface != "undefined") {
            //noinspection JSUnresolvedFunction
            window.glContentInterface.jsConsoleLog(msg);
        }
        else {
            console.log(msg);
        }
    };

    /*
     * Used to updated the padding on the bottom of the html to make
     * room for the audio player (or remove already added padding)
     */
     self.updateBottomMargin = function (paddingDelta) {
         var padding = document.documentElement.style.paddingBottom;
         if (padding == "") {
            padding = 0;
         }
         padding = parseInt(padding) + paddingDelta;
         document.documentElement.style.paddingBottom = padding + "px";
     }

    return self;

}();