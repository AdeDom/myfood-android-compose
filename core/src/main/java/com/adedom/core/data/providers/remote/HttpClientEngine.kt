package com.adedom.core.data.providers.remote

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO

interface AppHttpClientEngine {
    val engine: HttpClientEngine
}

class HttpClientEngineCIO : AppHttpClientEngine {
    override val engine: HttpClientEngine
        get() = CIO.create()
}
