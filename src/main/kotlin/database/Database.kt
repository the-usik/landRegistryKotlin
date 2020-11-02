package database

import com.mongodb.MongoClient

object Database {
    const val DB_HOST = "localhost:27017"
    const val DB_NAME = "land_registry"

    val client = MongoClient(DB_HOST)
    val database = client.getDatabase(DB_NAME)

    fun close() = client.close()
}