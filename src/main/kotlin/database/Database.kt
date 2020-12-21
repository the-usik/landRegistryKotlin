package database

import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.result.InsertManyResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.*
import org.bson.Document
import kotlin.reflect.KClass
import kotlin.reflect.full.createInstance

object Database {
    // database config
    private const val DB_HOST = "localhost:27017"
    private const val DB_NAME = "land_registry"

    // collection names
    private const val USERS_COLLECTION = "users"
    private const val LANDS_COLLECTION = "lands"
    private const val OWNERS_COLLECTION = "owners"
    private const val CATEGORIES_COLLECTION = "land_category"

    // connecting to database
    private val client = MongoClient(DB_HOST)
    private val database = client.getDatabase(DB_NAME)

    fun getUsers(): ObservableList<User> {
        val usersCollection = database.getCollection(USERS_COLLECTION)
        return usersCollection.find().asObservableModel(User::class)
    }

    fun getLands(): ObservableList<Land> {
        val landsCollection = database.getCollection(LANDS_COLLECTION)
        val landsModelCollection = landsCollection.find().asObservableModel(Land::class)

        for (land in landsModelCollection) {
            val ownerFilter = Document("_id", land.ownerId)
            val ownerFindResult = database.getCollection(OWNERS_COLLECTION).find(ownerFilter).first()

            val categoryFilter = Document("_id", land.categoryId)
            val categoryFindResult = database.getCollection(CATEGORIES_COLLECTION).find(categoryFilter).first()

            land.owner = if (ownerFindResult != null) Owner(ownerFindResult) else Owner()
            land.category = if (categoryFindResult != null) Category(categoryFindResult) else Category()
        }

        return landsModelCollection
    }

    fun getCategories(): ObservableList<Category> {
        val categoriesCollection = database.getCollection(CATEGORIES_COLLECTION)
        return categoriesCollection.find().asObservableModel(Category::class)
    }

    fun tryAuthUser(user: User): Boolean {
        val usersCollection = database.getCollection(USERS_COLLECTION)
        val document = Document()
        document.append("login", user.login)
        document.append("password", user.password)
        return usersCollection.find(document).count() > 0
    }

    fun insertLand(land: Land): InsertOneResult? {
        val ownerInsertId = insertOwner(land.owner).insertedId
        return if (ownerInsertId != null) {
            val ownerObjectId = ownerInsertId.asObjectId().value
            land.owner.id = ownerObjectId

            val landsCollection = database.getCollection(LANDS_COLLECTION)
            val landDocument = land.toDocument()

            landsCollection.insertOne(landDocument)
        } else null
    }

    fun updateLand(land: Land): UpdateResult {
        val updatedOwnerId = updateOwner(land.owner).upsertedId
        val landCollection = database.getCollection(LANDS_COLLECTION)
        val filterDocument = Document()
        val updateDocument = land.toDocument()
        updateDocument.get("ownerId", updatedOwnerId)
        filterDocument.append("_id", land.id)

        return landCollection.updateOne(filterDocument, updateDocument)
    }

    private fun updateOwner(owner: Owner): UpdateResult {
        val ownerCollection = database.getCollection(OWNERS_COLLECTION)
        val filterDocument = Document().apply { append("_id", owner.id) }

        return ownerCollection.updateOne(filterDocument, owner.toDocument())
    }

    fun insertOwner(landOwner: Owner): InsertOneResult {
        val ownerCollection = database.getCollection(OWNERS_COLLECTION)
        val ownerDocument = landOwner.toDocument()

        return ownerCollection.insertOne(ownerDocument)
    }

    private fun <T : DatabaseModel> FindIterable<Document>.asObservableModel(modelClass: KClass<T>): ObservableList<T> {
        val observableList = FXCollections.observableArrayList<T>()
        for (document in this) {
            val model = modelClass.createInstance()
            model.updateModel(document)
            observableList.add(model)
        }

        return observableList
    }
}