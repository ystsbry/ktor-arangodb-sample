import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val kotlin_version: String by project
val ktor_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.2.20"
    kotlin("plugin.serialization") version "2.2.20"
    id("io.ktor.plugin") version "3.3.0"
}

group = "com.example.com"
version = "0.0.1"

application {
    mainClass = "io.ktor.server.netty.EngineMain"
}

val openApiToolsVersion = "5.3.0"

dependencies {
    implementation("io.github.smiley4:ktor-openapi:$openApiToolsVersion")
    implementation("io.github.smiley4:ktor-swagger-ui:$openApiToolsVersion")
    implementation("io.ktor:ktor-server-swagger")
    implementation("io.ktor:ktor-server-core")
    implementation("io.ktor:ktor-server-netty")
    implementation("io.ktor:ktor-server-resources:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")
    implementation("com.arangodb:arangodb-java-driver:7.22.0")

    testImplementation("io.ktor:ktor-server-test-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.register<JavaExec>("runDev") {
    group = "application"
    description = "Run Ktor in development mode"
    dependsOn("classes")
    mainClass.set(application.mainClass)
    classpath = sourceSets["main"].runtimeClasspath
    jvmArgs("-Dio.ktor.development=true")
}

tasks.register<JavaExec>("exportOpenApi") {
    group = "documentation"
    description = "Generate OpenAPI JSON from current Ktor routes"
    mainClass.set("com.example.com.OpenApiExportKt") // 上で作ったファイル名に合わせる
    classpath = sourceSets["main"].runtimeClasspath
}
