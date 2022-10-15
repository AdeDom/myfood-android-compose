package com.adedom.data.models

import com.myfood.server.data.models.base.BaseError
import com.myfood.server.data.models.web_sockets.FavoriteWebSocketsResponse
import com.myfood.server.utility.constant.ResponseKeyConstant

@kotlinx.serialization.Serializable
data class MyFavoriteResponse(
    var version: String = ResponseKeyConstant.VERSION,
    var status: String = ResponseKeyConstant.ERROR,
    var result: FavoriteWebSocketsResponse? = null,
    var error: BaseError? = null,
)