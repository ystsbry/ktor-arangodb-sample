package shared.arangodb

import com.typesafe.config.ConfigFactory
import com.arangodb.ArangoDB

object ArangoClient {

    private val cfg = ConfigFactory.load().getConfig("arango")

    private val host = cfg.getString("host")
    private val port = cfg.getInt("port")
    private val user = cfg.getString("user")
    private val pass = cfg.getString("password")
    
    @PublishedApi
    internal val defaultDb: String = cfg.getString("db")

    val client: ArangoDB by lazy {
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
        name: String = defaultDb,
        createIfMissing: Boolean = true
    ) = client.db(name).also { db ->
        if (createIfMissing && !db.exists()) {
            client.createDatabase(name)
        }
    }

    inline fun <T> withDb(
        name: String = defaultDb,
        block: (com.arangodb.ArangoDatabase) -> T
    ): T = block(database(name))
}
