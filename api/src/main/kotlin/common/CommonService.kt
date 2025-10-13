package common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import shared.arangodb.ArangoClient
import shared.error.ConflictException

object CommonService {
    suspend fun createCollectionIfAbsent(name: String) = withContext(Dispatchers.IO) {
        if (ArangoClient.db.collection(name).exists()) {
            throw ConflictException("This collection with the name already exists in ArangoDB (collection name: $name)")
        }

        try {
            ArangoClient.db.createCollection(name)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}
