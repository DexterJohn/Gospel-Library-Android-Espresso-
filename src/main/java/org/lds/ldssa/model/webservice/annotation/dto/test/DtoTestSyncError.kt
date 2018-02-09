package org.lds.ldssa.model.webservice.annotation.dto.test

import com.google.gson.annotations.SerializedName

class DtoTestSyncError {
    var id = ""
    @SerializedName("msg")
    var message = ""
}
