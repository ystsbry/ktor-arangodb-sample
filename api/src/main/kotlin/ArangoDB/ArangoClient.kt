package ArangoDB

import com.arangodb.ArangoDB
import com.arangodb.ContentType
import com.arangodb.serde.jackson.JacksonSerde

object ArangoClient {

    val client: ArangoDB by lazy {
        val host = System.getenv("ARANGO_HOST") ?: "127.0.0.1"
        val port = (System.getenv("ARANGO_PORT") ?: "8529").toInt()
        val user = System.getenv("ARANGO_USER") ?: "root"
        val pass = System.getenv("ARANGO_PASSWORD") ?: ""

        ArangoDB.Builder()
            .host(host, port)
            .user(user)
            .password(pass)
            .build()
            .also { c ->
                Runtime.getRuntime().addShutdownHook(Thread { c.shutdown() })
            }
    }

    fun database(
        name: String = System.getenv("ARANGO_DB") ?: "test",
        createIfMissing: Boolean = true
    ) = client.db(name).also { db ->
        if (createIfMissing && !db.exists()) {
            client.createDatabase(name)
        }
    }

    inline fun <T> withDb(
        name: String = System.getenv("ARANGO_DB") ?: "test",
        block: (com.arangodb.ArangoDatabase) -> T
    ): T = block(database(name))
}
