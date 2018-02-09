// Namespace
var LDS = LDS || {};

// test module
LDS.scripttest = function () {
    var self = {};

    // ====== Text Marks ======
    self.mark1Word = function () {
        LDS.main.markTextItems("dreamed")
    }

    self.mark2Words = function () {
        LDS.main.markTextItems("Laban,Jews")
    }

    self.markPhrase = function () {
        LDS.main.markTextItems("tent of my father")
    }

    self.markPhraseWithSup = function () {
        LDS.main.markTextItems("shall return to Jerusalem")
    }


    // ====== Paragraph ======
    self.removeAllParagraphMarks = function () {
        LDS.main.removeAllParagraphMarks();
    }

    self.scrollToParagraph = function () {
        LDS.main.scrollToParagraphByAid("19067681")
    }

    self.scrollAndMarkParagraph = function () {
        LDS.main.scrollAndMarkParagraphsByAid("19067681", "19067681,19067683,19067684")
    }

    function consoleLog(msg) {
        LDS.util.consoleLog(msg);
    }


    // ====== Annotations ======
    self.clearAnnotations = function () {
        LDS.annotation.clearAnnotations();
    }

    self.highlight1 = function () {
        LDS.annotation.showHighlight('{' +
            '"colorName":"pink",' +
            '"colorValue":"f49ac1",' +
            '"device":"android",' +
            '"docId":"19133528",' +
            '"hasContent":false,' +
            '"highlights":[' +
            '{"aid":"19067675","colorName":"pink", "colorValue":"fff200","startOffset":"4","endOffset":"6"}' +
            '],' +
            '"languageId":0,' +
            '"links":[],' +
            '"source":"",' +
            '"status":"",' +
            //'"style":"red-underline",' +
            '"style":"",' +
            '"tags":[],' +
            '"type":"highlight",' +
            '"id":"unique-guid-1"}');
    }

    self.highlight2 = function () {
        LDS.annotation.showHighlight('{' +
            '"colorName":"pink",' +
            '"colorValue":"f49ac1",' +
            '"device":"android",' +
            '"docId":"19133528",' +
            '"hasContent":false,' +
            '"highlights":[' +
            '{"aid":"19067676","colorName":"pink","colorValue":"fff200","startOffset":"4","endOffset":"15"}' +
            ',' +
            '{"aid":"19067677","colorName":"pink","colorValue":"fff200","startOffset":"-1","endOffset":"5"}' +
            '],' +
            '"languageId":0,' +
            '"links":[],' +
            '"source":"",' +
            '"status":"",' +
            '"style":"",' +
            '"tags":[],' +
            '"type":"highlight",' +
            '"id":"unique-guid-2"}');
    }

    self.highlightOverlap = function () {
        LDS.annotation.showHighlight('{' +
            '"colorName":"pink",' +
            '"colorValue":"f49ac1",' +
            '"device":"android",' +
            '"docId":"19133528",' +
            '"hasContent":false,' +
            '"highlights":[' +
            '{"aid":"19067675","colorName":"pink", "colorValue":"fff200","startOffset":"4","endOffset":"6"}' +
            '],' +
            '"languageId":0,' +
            '"links":[],' +
            '"source":"",' +
            '"status":"",' +
                //'"style":"red-underline",' +
            '"style":"",' +
            '"tags":[],' +
            '"type":"highlight",' +
            '"id":"unique-guid-3"}');
        LDS.annotation.showHighlight('{' +
            '"colorName":"green",' +
            '"device":"android",' +
            '"docId":"19133528",' +
            '"hasContent":false,' +
            '"highlights":[' +
            '{"aid":"19067675","colorName":"green", "startOffset":"5","endOffset":"8"}' +
            '],' +
            '"languageId":0,' +
            '"links":[],' +
            '"source":"",' +
            '"status":"",' +
                //'"style":"red-underline",' +
            '"style":"",' +
            '"tags":[],' +
            '"type":"highlight",' +
            '"id":"unique-guid-4"}');
    }


    // ====== Bookmark ribbon ======

    self.showBookmarkIndicator = function () {
        LDS.annotation.showBookmarkIndicator("unique-guid-1", "19067676")
    }

    self.applyRedRibbonEffects = function () {
        LDS.annotation.applyRedRibbonEffects("unique-guid-1")
    }


    // ====== Annotation ======
    self.topVisibleAid = function () {
        var aid = LDS.annotation.findTopVisibleAid();
        LDS.util.consoleLog("aid: " + aid);
    }

    return self;

}();