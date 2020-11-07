package models

import org.bson.Document

interface DatabaseModel {
    fun toDocument(): Document
    fun updateModel(document: Document)
}
