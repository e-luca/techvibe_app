package com.projects.techvibe.model.access

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class RolesConverter : AttributeConverter<Set<UserRoles>, String> {

    override fun convertToDatabaseColumn(attribute: Set<UserRoles>?): String {
        if (attribute.isNullOrEmpty()) return ""
        return attribute.joinToString(",") { it.name }
    }

    override fun convertToEntityAttribute(dbData: String?): Set<UserRoles> {
        if (dbData.isNullOrEmpty()) return emptySet()
        val roles = dbData.split(",").map { UserRoles.valueOf(it) }
        return roles.toSet()
    }
}
