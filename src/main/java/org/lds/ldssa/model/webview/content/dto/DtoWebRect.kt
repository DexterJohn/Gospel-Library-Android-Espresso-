package org.lds.ldssa.model.webview.content.dto

import java.io.Serializable

class DtoWebRect : Serializable {
    var top: Double = 0.0
    var bottom: Double = 0.0
    var left: Double = 0.0
    var right: Double = 0.0
    var height: Double = 0.0
    var width: Double = 0.0

    fun inBounds(xPos: Int, yPos: Int): Boolean {
        return xPos >= left && xPos <= right && yPos >= top && yPos <= bottom
    }
}
