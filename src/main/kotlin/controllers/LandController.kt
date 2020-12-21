package controllers

import database.Database
import models.Land
import models.LandModel
import tornadofx.Controller

class LandController : Controller() {
    val categories = Database.getCategories()

    fun addLand(land: Land): Boolean {
        val insertId = Database.insertLand(land)
        return insertId?.insertedId != null
    }

    fun editLand(land: Land) {
        println("success")
    }
}