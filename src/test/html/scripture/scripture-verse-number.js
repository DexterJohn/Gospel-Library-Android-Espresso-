document.addEventListener("DOMContentLoaded", function() {
	var verse = document.getElementsByClassName("verse");
	for (var i=0; i<verse.length; i++) {
		var verseNumber = verse[i].getElementsByClassName("verse-number")[0];
		var line = verse[i].getElementsByClassName("line");
		if (line.length > 0){
			verse[i].className = verse[i].className + " contains-line";
			if (/^[0-9]{2} *$/.test(verseNumber.textContent)) {
				verse[i].className += " digits-2";
			} else if (/^[0-9]{3} *$/.test(verseNumber.textContent)) {
				verse[i].className += " digits-3";
			}
		}
		//If verse contains poetry but starts with text node, don't float the verse-number.
		if(verse[i].childNodes[0].className == "verse-number" && verse[i].getElementsByClassName("line").length > 0){
			if(verse[i].childNodes[1].nodeType == 3){
				verse[i].className += " no-float";
			}
		}
	}
});