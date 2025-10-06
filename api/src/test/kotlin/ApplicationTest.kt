package com.example.com

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals
import io.ktor.server.config.*

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        environment {
            // テスト用の arango.* を注入
            config = MapApplicationConfig(
                "arango.host" to "127.0.0.1",
                "arango.port" to "8529",
                "arango.user" to "root",
                "arango.password" to "password",
                "arango.db" to "tutorial",
            )
        }
        application {
            module()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
