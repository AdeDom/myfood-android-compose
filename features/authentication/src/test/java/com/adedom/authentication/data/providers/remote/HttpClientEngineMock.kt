package com.adedom.authentication.data.providers.remote

import com.adedom.core.data.providers.remote.AppHttpClientEngine
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.client.request.*

class HttpClientEngineMock(
    private val handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
) : AppHttpClientEngine {
    override val engine: HttpClientEngine
        get() = MockEngine(handler)
}