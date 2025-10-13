package com.example.com

import io.ktor.server.application.*
import io.github.smiley4.ktoropenapi.OpenApi
import io.github.smiley4.ktoropenapi.config.OutputFormat
import io.github.smiley4.ktoropenapi.config.OpenApiPluginConfig
import io.ktor.server.resources.Resources
import io.github.smiley4.ktoropenapi.OpenApiPlugin
import kotlinx.serialization.json.Json
import io.ktor.server.application.ApplicationStopping
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json 
import java.io.File
import shared.arangodb.ArangoClient
import shared.error.configureErrorMapper

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(Resources)

    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                explicitNulls = false
                prettyPrint = false
            }
        )
    }

    install(OpenApi) {
        info { title = "My API"; version = "1.0.0" }
        outputFormat = OutputFormat.YAML
        autoDocumentResourcesRoutes = true
    }

    monitor.subscribe(ApplicationStarted) {
        OpenApiPlugin.generateOpenApiSpecs(this@module)
        val spec = OpenApiPlugin.getOpenApiSpec(OpenApiPluginConfig.DEFAULT_SPEC_ID)

        val out = File("src/main/resources/openapi/documentation.yaml")
        out.parentFile?.mkdirs()
        out.writeText(spec)
        log.info("OpenAPI spec exported to: ${out.absolutePath}")
    }

    ArangoClient.init(environment.config)
    monitor.subscribe(ApplicationStopping) { ArangoClient.close() }

    configureHTTP()
    configureRouting()
    configureErrorMapper()
}
