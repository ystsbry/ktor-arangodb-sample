package com.example.com

import io.ktor.server.application.*
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.OutputFormat
import shared.arangodb.ArangoClient
import io.ktor.server.application.ApplicationStopping

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(OpenApi) {
        info { title = "My API"; version = "1.0.0" }
        outputFormat = OutputFormat.YAML
    }

    ArangoClient.init(environment.config)
    monitor.subscribe(ApplicationStopping) { ArangoClient.close() }

    configureHTTP()
    configureRouting()
}
