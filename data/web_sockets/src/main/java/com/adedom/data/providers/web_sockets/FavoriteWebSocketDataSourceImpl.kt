package com.adedom.data.providers.web_sockets

import com.adedom.core.data.providers.data_store.AppDataStore
import com.adedom.core.data.providers.remote.DataProviderRemote
import com.adedom.data.models.MyFavoriteResponse
import com.adedom.data.web_sockets.BuildConfig
import com.myfood.server.data.models.request.MyFavoriteRequest
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.websocket.*
import kotlinx.coroutines.flow.*
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