// Namespace
var LDS = LDS || {};

window.glContentInterface = window.glContentInterface || undefined;

// main module
LDS.media = function () {
    var self = {};

    /* =================================
     * Videos
     * =================================== */
    self.configureVideos = function () {
        var videos = document.getElementsByTagName('video');
        var videosArray = new Array();
        for (var i = 0, n = videos.length; i < n; i++) {
            videosArray.push(configureVideo(i, videos[i]));
        }
        var jsonVideos = {'videos': videosArray};
        window.glContentInterface.jsReportInlineVideoInfo(JSON.stringify(jsonVideos, null));
    }

    function configureVideo(videoIndex, videoElement) {
        videoElement.videoIndex = videoIndex;
        var videoId = videoElement.getAttribute("data-video-id");

        //Registers the play and download buttons
        var playButton = document.getElementsByClassName('video_button_play')[videoIndex];
        var downloadButton = document.getElementsByClassName('video_button_download')[videoIndex];

        // get the video id and apply it to the video button icons
        var playVideoIdAttr = document.createAttribute("data-video-id");
        playVideoIdAttr.value = videoId;
        playButton.setAttributeNode(playVideoIdAttr);

        var downloadVideoIdAttr = document.createAttribute("data-video-id");
        downloadVideoIdAttr.value = videoId;
        downloadButton.setAttributeNode(downloadVideoIdAttr);

        return getVideoInfo(videoElement);
    }

    function getVideoInfo(videoElement) {
        var sources = videoElement.getElementsByTagName('source-no-preload');
        if (sources.length == 0) {
            sources = videoElement.getElementsByTagName('source');
        }

        var sourcesArray = new Array();
        for (var i = 0, n = sources.length; i < n; i++) {
            var source = sources[i];
            var sourceJson = {
                'src': source.getAttribute('src'),
                'type': source.getAttribute('type'),
                'data-container': source.getAttribute('data-container'),
                'data-encodingbitspersec': source.getAttribute('data-encodingbitspersec'),
                'data-width': source.getAttribute('data-width'),
                'data-height': source.getAttribute('data-height'),
                'data-sizeinbytes': source.getAttribute('data-file-size'),
                'data-durationms': source.getAttribute('data-durationms'),
                'data-alloweduses': source.getAttribute('data-alloweduses')
            };
            sourcesArray.push(sourceJson);
        }

        // get the index, title and id, if present
        var videoId = null;
        var videoTitle = null;
        var videoIndex = -1;
        if ("videoIndex" in videoElement) {
            videoIndex = videoElement.videoIndex;
        }

        videoTitle = videoElement.getAttribute("data-video-title");
        videoId = videoElement.getAttribute("data-video-id");

        var jsonVideo = {
            'id': videoId,
            'title': videoTitle,
            'index': videoIndex,
            'sources': sourcesArray
        };

        return jsonVideo;
    }


    function preventDefault(event) {
        event.preventDefault();
    }



    /* =================================
     * IMAGES
     * =================================== */
    self.configureImages = function () {
        var images = document.getElementsByTagName('img');
        var imagesArray = new Array();
        for (var i = 0, n = images.length; i < n; i++) {
            imagesArray.push(configureImage(i, images[i]));
        }
        
        var jsonImages = {'images': imagesArray};
        window.glContentInterface.jsReportImageInfo(JSON.stringify(jsonImages, null));
    }
    
    function configureImage(imageIndex, imageElement) {
        return getImageInfo(imageIndex, imageElement);
    }
    
    function getImageInfo(imageIndex, imageElement) {
        var attributes = imageElement.attributes;
        var sourcesArray = new Array();
        var srcAttr;

        //Populates the sourcesArray with all the multi-src sources
        for (var i = 0, n = attributes.length; i < n; i++) {
            var attribute = attributes[i];
            if (attribute.name.startsWith("data-src")) {

                //Splits the name in to the three sections "data", "src', and "WIDTHxHEIGHT"
                var parts = attribute.name.split("-");
                if (parts.length != 3) {
                    continue;
                }
                
                //Splits the remaining part in to the width and height
                parts = parts[2].split("x");
                if (parts.length != 2) {
                    continue;
                }

                var srcWidth = parts[0];
                var srcHeight = parts[1];
                
                var sourceJson = {
                    'data-width': srcWidth,
                    'data-height': srcHeight,
                    'src':attribute.value
                };
                sourcesArray.push(sourceJson);
            }

            if (attribute.name.startsWith("src")) {
                srcAttr = {
                    'data-width': "1",
                    'data-height': "1",
                    'src':attribute.value
                };
            }
        }

        // If the image doesn't have multiple sources, but does have a single source, then add it to the sources list
        if (sourcesArray.length == 0) {
            sourcesArray.push(srcAttr)
        }
        
        //Creates the JSON representation of the image object
        var jsonImage = {
            'id': imageElement.getAttribute("id"),
            'title': imageElement.getAttribute("alt"),
            'index': imageIndex,
            'sources': sourcesArray
        };

        return jsonImage;
    }

    return self;

}();