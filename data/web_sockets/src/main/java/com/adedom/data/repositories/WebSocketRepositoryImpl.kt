package com.adedom.data.repositories

class WebSocketRepositoryImpl : WebSocketRepository {

    override fun getMessage(): String {
        return "Hello, web sockets."
    }
}