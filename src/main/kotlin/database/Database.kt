package database

import com.mongodb.MongoClient

object Database {
    private const val DB_HOST = "localhost:27017"
    private const val DB_NAME = "land_registry"

    private val client = MongoClient(DB_HOST)
    private val database = client.getDatabase(DB_NAME)

    fun getDatabase() = database
    fun close() = client.close()
}