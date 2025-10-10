package common

import shared.arangodb.ArangoClient

object CommonService {
    suspend fun createCollectionIfAbsent(name: String): Result<Unit> = runCatching {
        try {
            if (ArangoClient.db.collection(name).exists()) {
                return@runCatching
            }

            ArangoClient.db.createCollection(name)
        } catch (e: Exception) {
            e.printStackTrace()

        }
    }
}
