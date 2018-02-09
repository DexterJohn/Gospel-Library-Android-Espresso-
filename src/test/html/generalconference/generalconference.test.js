// Namespace
var LDS = LDS || {};

// test module
LDS.gctest = function () {
    var self = {};

    // ====== Text Marks ======
    self.mark1Word = function () {
        LDS.main.markTextItems("scattereth")
    }

    self.mark2Words = function () {
        LDS.main.markTextItems("Zeniff,multitude")
    }

    self.markPhrase = function () {
        LDS.main.markTextItems("understanding. Though")
    }

    self.markPhraseWithSup = function () {
        LDS.main.markTextItems("end of speaking")
    }


    // ====== Paragraph ======
    self.removeAllParagraphMarks = function () {
        LDS.main.removeAllParagraphMarks();
    }

    self.scrollToParagraph = function () {
        LDS.main.scrollToParagraphByAid("7641589")
    }

    self.scrollAndMarkParagraph = function () {
        LDS.main.scrollAndMarkParagraphsByAid("7641589", "7641589,7641591,7641592")
    }

    function consoleLog(msg) {
        LDS.util.consoleLog(msg);
    }


    // ====== Annotations ======
    self.clearAnnotations = function () {
        LDS.annotation.clearAnnotations();
    }

    // highlight verse 2 "caused"
    self.highlight = function () {
        LDS.annotation.showHighlight('{' +
            '"colorName":"pink",' +
            '"colorValue":"f49ac1",' +
            '"device":"android",' +
            '"docId":"19133528",' +
            '"hasContent":false,' +
            '"highlights":[' +
            '{"aid":"7641588","colorName":"pink","colorValue":"fff200","endOffset":"4","startOffset":"4"}' +
            '],' +
            '"languageId":0,' +
            '"links":[],' +
            '"source":"",' +
            '"status":"",' +
            '"style":"",' +
            '"tags":[],' +
            '"type":"highlight",' +
            '"id":"543eeda1-7665-4fe5-b4ef-dfffc2ee231d"}');
    }


    return self;

}();