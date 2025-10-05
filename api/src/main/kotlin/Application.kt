package com.example.com

import io.ktor.server.application.*
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.OutputFormat

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(OpenApi) {
        info { title = "My API"; version = "1.0.0" }
        outputFormat = OutputFormat.JSON  // JSON で生成（YAMLにも変更可）
    }

    configureHTTP()
    configureRouting()
}
