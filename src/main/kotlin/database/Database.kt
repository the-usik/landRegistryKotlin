package database

import com.mongodb.MongoClient
import com.mongodb.client.FindIterable
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import models.DatabaseModel
import models.User
import models.UserModel
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
    private const val OWNERS_COLLECTION = "land_owners"
    private const val CATEGORIES_COLLECTION = "categories"

    // connecting to database
    private val client = MongoClient(DB_HOST)
    private val database = client.getDatabase(DB_NAME)

    fun getDatabase() = database

    fun close() = client.close()

    fun loadUsers(): ObservableList<User> {
        val usersCollection = database.getCollection(USERS_COLLECTION)
        return usersCollection.find().asObservableModel(User::class)
    }

    fun tryAuthUser(user: UserModel): Boolean {
        val usersCollection = database.getCollection(USERS_COLLECTION)
        val document = Document()
        document.append("login", user.login.value)
        document.append("password", user.password.value)
        val findResult = usersCollection.find(document)
        return findResult.count() > 0
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