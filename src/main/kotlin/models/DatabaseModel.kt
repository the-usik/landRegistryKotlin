package models

import org.bson.Document

interface DatabaseModel {
    fun updateModel(document: Document)
}
