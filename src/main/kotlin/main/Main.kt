package main

import io.ktor.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

object Main {
    private val CONF_PORT = "PORT"
    private val DEFAULT_PORT = 8080

    @JvmStatic fun main(args: Array<String>) {
        val port = System.getenv(CONF_PORT)?.toInt() ?: DEFAULT_PORT

        embeddedServer(Netty, port, module = Application::main).start(wait = true)
    }

}