(function() {

  function MultiSrcImages() {}

  window.MultiSrcImages = MultiSrcImages;

  MultiSrcImages.init = function() {
    var that = this;

    var existing_onload = window.onload || new Function;
    window.onload = function() {
      var images = document.getElementsByTagName("img"), i, n, image;
      that.multiSrcImages = [];
      for (i = 0, n = images.length; i < n; i++) {
        image = images[i];
        that.multiSrcImages.push(new MultiSrcImage(image));
      }
      existing_onload();
    }
    
    var existing_onresize = window.onresize || new Function;
    window.onresize = function() {
      var i, n, multiSrcImage;
      for (i = 0, n = that.multiSrcImages.length; i < n; i++) {
        multiSrcImage = that.multiSrcImages[i];
        multiSrcImage.swap();
      }
      existing_onresize();
    }
  };

  function MultiSrcImage(el) {
    this.el = el;
    this.swap();
  }

  window.MultiSrcImage = MultiSrcImage;
  
  MultiSrcImage.prototype.get_available_width = function() {
    var maxWidth = window.getComputedStyle(this.el)['max-width'];
    
    // Is it a percentage value?
    var result = maxWidth.match(/^\+?(\d*(?:\.\d+)?)%$/);
    if (result) {
      var parentNode = this.el.parentNode;
      while (window.getComputedStyle(parentNode).display == 'inline') {
        parentNode = parentNode.parentNode;
      }
      return Math.round(Number(parentNode.offsetWidth * result[1] / 100));
    }

    // Is it a pixel value?
    result = maxWidth.match(/^\+?(\d*(?:\.\d+)?)(?:px)?$/);
    if (result) {
      return Number(result[1]);
    }
    
    return this.el.offsetWidth;
  }
  
  MultiSrcImage.prototype.get_best_src = function() {
    var avail_width = this.get_available_width(), i, n, attribute, result, width, height;
    
    if (window.devicePixelRatio > 1) {
      avail_width *= 2;
    }

    best_width = 0;
    best_src = undefined;
    
    for (i = 0, n = this.el.attributes.length; i < n; i++) {
      attribute = this.el.attributes[i];
      result = attribute.name.match(/^data-src-(\d+)x(\d+)$/);
      if (result) {
        width = Number(result[1]);
        if (width > best_width && best_width < avail_width) {
          best_width = width;
          best_src = attribute.value;
        }
      }
    }
    
    return best_src;
  }

  MultiSrcImage.prototype.swap = function() {
    var that = this;
    function load() {
      if (!that.el.complete) {
        setTimeout(load, 5);
      } else {
        new_src = that.get_best_src();
        if (typeof new_src !== 'undefined' && that.el.src != new_src) {
          that.el.setAttribute('src', new_src);
        }
      }
    }
    load();
  }

  MultiSrcImages.init();

})();
