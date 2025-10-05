package com.example.com

import io.github.smiley4.ktoropenapi.OpenApiPlugin
import io.github.smiley4.ktoropenapi.config.OpenApiPluginConfig
import io.ktor.server.application.*
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import java.io.File

// OpenAPI JSON を出力して終了する専用エントリ
fun main() {
    // あなたの module を使ってアプリを"起動だけ"する（ポート0でOK）
    embeddedServer(Netty, port = 0, module = Application::module).apply {
        start(wait = false)

        // ルートを走査してOpenAPIを生成
        OpenApiPlugin.generateOpenApiSpecs(application)

        // 既定スペック名で文字列を取得
        val json = OpenApiPlugin.getOpenApiSpec(OpenApiPluginConfig.DEFAULT_SPEC_ID)

        // build配下に出力（src配下へ書くのは避けるのが吉）
        val out = File("api.json")
        out.parentFile?.mkdirs()
        out.writeText(json)

        stop(1000, 2000)
    }
}