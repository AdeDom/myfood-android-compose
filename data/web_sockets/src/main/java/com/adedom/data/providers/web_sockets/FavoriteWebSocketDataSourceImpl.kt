package com.adedom.data.providers.web_sockets

import com.adedom.core.data.providers.datastore.AppDataStore
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.data.models.MyFavoriteResponse
import com.adedom.data.web_sockets.BuildConfig
import com.myfood.server.data.models.request.MyFavoriteRequest
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.header
import io.ktor.http.DEFAULT_PORT
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class FavoriteWebSocketDataSourceImpl(
    private val dataProviderRemote: DataProviderRemote,
    private val appDataStore: AppDataStore,
) : FavoriteWebSocketDataSource {

    private var webSocketSession: WebSocketSession? = null

    override suspend fun init(): Unit? {
        return try {
            val accessToken = appDataStore.getAccessToken()
            webSocketSession = dataProviderRemote.getHttpClient()
                .webSocketSession(
                    method = HttpMethod.Get,
                    host = BuildConfig.HOST,
                    port = DEFAULT_PORT,
                    path = "/ws/favorite/myFavorite",
                ) {
                    header(HttpHeaders.Authorization, accessToken)
                }
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    override fun isActive(): Boolean {
        return webSocketSession?.isActive ?: false
    }

    override fun observe(): Flow<MyFavoriteResponse> {
        return try {
            webSocketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.map { frame ->
                    val text = (frame as Frame.Text).readText()
                    Json.decodeFromString(text)
                } ?: flow { }
        } catch (e: Throwable) {
            e.printStackTrace()
            flow { }
        } finally {
            webSocketSession = null
        }
    }

    override suspend fun send(foodId: Int) {
        val request = MyFavoriteRequest(foodId)
        val json = Json.encodeToString(request)
        val text = Frame.Text(json)
        return webSocketSession?.outgoing?.send(text) ?: throw Throwable(json)
    }

    override suspend fun close(): Unit? {
        return webSocketSession?.close()
    }
}