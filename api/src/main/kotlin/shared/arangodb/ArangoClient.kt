package shared.arangodb

import com.arangodb.ArangoDB
import com.arangodb.ArangoDatabase
import io.ktor.server.application.*
import io.ktor.server.config.ApplicationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.Closeable

object ArangoClient : Closeable {
    @Volatile private var driver: ArangoDB? = null
    @Volatile lateinit var db: ArangoDatabase
        private set

    fun init(config: ApplicationConfig) {
        if (driver != null) return

        val host = config.property("arango.host").getString()
        val port = config.property("arango.port").getString().toInt()
        val user = config.property("arango.user").getString()
        val pass = config.property("arango.password").getString()
        val dbName = config.property("arango.db").getString()

        val d = ArangoDB.Builder()
            .host(host, port)
            .user(user)
            .password(pass)
            .build()

        driver = d
        db = d.db(dbName)
    }

    override fun close() {
        driver?.shutdown()
        driver = null
    }
}
