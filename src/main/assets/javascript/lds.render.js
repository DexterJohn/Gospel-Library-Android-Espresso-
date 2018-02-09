// This Java script file is used to help the Java code to know when the html content is sufficiently rendered to place down highlights/annotations

// Namespace
var LDS = LDS || {};
LDS.finish_render = {};

//noinspection JSUnusedLocalSymbols
LDS.finish_render.transitionListener = function(e) {
    //noinspection JSUnresolvedVariable
    if (typeof window.glContentInterface != "undefined") {
        //noinspection JSUnresolvedVariable,JSUnresolvedFunction // give a slight delay here
        setTimeout(function() { window.glContentInterface.jsFinishedRendering(window.devicePixelRatio); }, 300);
    }
    LDS.finish_render.resetTransitionElement();
};

LDS.finish_render.loadTransitionElement = function() {
    //console.log("LOAD TRANSITION ELEMENT");
    document.getElementById('transitionElement').className = 'finish';
    document.getElementById('transitionElement').addEventListener('webkitTransitionEnd', LDS.finish_render.transitionListener);
};

LDS.finish_render.resetTransitionElement = function() {
    document.getElementById('transitionElement').removeEventListener('webkitTransitionEnd', LDS.finish_render.transitionListener);
    document.getElementById('transitionElement').className = '';
};

window.onload = LDS.finish_render.loadTransitionElement;