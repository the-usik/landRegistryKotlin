package database

import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import com.mongodb.client.result.InsertOneResult
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

    fun getDatabase() = database

    fun close() = client.close()

    fun loadUsers(): ObservableList<User> {
        val usersCollection = database.getCollection(USERS_COLLECTION)
        return usersCollection.find().asObservableModel(User::class)
    }

    fun loadCategories(): ObservableList<Category> {
        val categoriesCollection = database.getCollection(CATEGORIES_COLLECTION)
        return categoriesCollection.find().asObservableModel(Category::class)
    }

    fun tryAuthUser(user: UserModel): Boolean {
        val usersCollection = database.getCollection(USERS_COLLECTION)
        val document = Document()
        document.append("login", user.login.value)
        document.append("password", user.password.value)
        val findResult = usersCollection.find(document)
        return findResult.count() > 0
    }

    fun insertLandOwner(landOwner: Owner): InsertOneResult {
        val ownerCollection = database.getCollection(OWNERS_COLLECTION)
        val ownerDocument = landOwner.toDocument()

        return ownerCollection.insertOne(ownerDocument)
    }

    fun insertLand(land: Land): InsertOneResult {
        val landsCollection = database.getCollection(LANDS_COLLECTION)
        val landDocument = land.toDocument()

        return landsCollection.insertOne(landDocument)
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